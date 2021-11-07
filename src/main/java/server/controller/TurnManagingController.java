package main.java.server.controller;

import main.java.shared.message.*;
import main.java.shared.socket.Client;
import main.java.shared.observer.Observable;
import main.java.server.model.Model;
import main.java.server.view.VirtualView;
import main.java.shared.observer.Observer;
import main.java.shared.utils.CircularList;


/**The tunManagingController is responsible for turn phase of the game. It shows the possible actions, asks to make
 * and action and checks on a possible win or loss.
 * Every response is forwarded to the model which gets updated.
 *
 * It doesn't communicate directly with the client, instead it communicates with the virtualView which receives events
 * from the client through the network and vice versa.
 *
 * It is observer of the virtual view's PlayerResponse and observable to the Virtual View's
 * ControllerMessageSender.
 */






public class TurnManagingController extends Observable<ControllerMessage> implements Observer<ClientMessage>, Runnable {

    private final Controller controller;

    private final Model model;

    private final CircularList<VirtualView> virtualViews;

    private final CircularList<String> usernames;

    private String currentPlayer;

    private static int numPlayers;



    private static Client.Unlock confirmLock;

    private static Client.Unlock responseLock;

    private final TurnManagingControllerStateMachine turnManagingControllerStateMachine;

    private boolean currentTurn = true;






    /**
     * TurnManagingController's constructor
     * @param c controller
     * @param m model
     * @param names players' usernames
     * @param vViews players' virtualViews
     * @param n number of players
     */

    public TurnManagingController(Controller c, Model m, CircularList<String> names, CircularList<VirtualView> vViews, int n){
        this.controller = c;
        this.model = m;
        this.usernames = names;
        this.virtualViews = vViews;
        numPlayers = n;

        for(int i=0; i < virtualViews.size(); i++){
            this.addObserver(virtualViews.get(i).getControllerMessageSender());
            virtualViews.get(i).getPlayerResponse().addObserver(this);
            virtualViews.get(i).setUsername(usernames.get(i));
        }

        confirmLock = new Client.Unlock();
        responseLock = new Client.Unlock();
        this.turnManagingControllerStateMachine = new TurnManagingControllerStateMachine();

    }







    /**
     * Events that happen during the turn phase of the game
     */

    public enum TurnManagingControllerEvents{
        NOTIFY_REQUEST_TO_ONE_PLAYER,
        SHOWING_POSSIBLE_PLAYER_POSSIBLE_ACTIONS,
        ASKING_PLAYER_TO_MAKE_AN_ACTION,
        INFORMING_PLAYER_LOSS,
        INFORMING_PLAYER_WIN
    }









    /**
     * Strategies that are used by the TurnManagingControllerStateMachine based on the event
     */

    public interface PostEventStrategies{
        void notifyRequestToOnePlayerStrategy();
        void receivingAPlayerResponseStrategy();
        void receivingAPlayerConfirmationStrategy();

    }







    /**States which the controllerStateMachine can be at based on the occurring events.
     * They determine the implementation of the different locks which work on the game's
     * synchronization*/


    public enum TurnMangingControllerStates implements PostEventStrategies{
        SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE{
            @Override
            public void notifyRequestToOnePlayerStrategy() {
                confirmLock.reset();
                responseLock.reset();
                confirmLock.setLatch(numPlayers);
            }

            @Override
            public void receivingAPlayerResponseStrategy() {

            }

            @Override
            public void receivingAPlayerConfirmationStrategy() {

            }
        },

        WAITING_FOR_A_PLAYER_RESPONSE_STATE{
            @Override
            public void notifyRequestToOnePlayerStrategy() {

            }

            @Override
            public void receivingAPlayerResponseStrategy() {
                try {
                    responseLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void receivingAPlayerConfirmationStrategy() {
                try{
                    confirmLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }








    /**
     * the TurnManagingControllerStateMachine is held accountable for the beginning of the game's flow.
     **/

    public static class TurnManagingControllerStateMachine{

        TurnMangingControllerStates currentState;

        public void triggeringEvent(TurnManagingControllerEvents e){

            if(e == TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER){
                currentState = TurnMangingControllerStates.SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE;
                currentState.notifyRequestToOnePlayerStrategy();
            }

            else if(e == TurnManagingControllerEvents.ASKING_PLAYER_TO_MAKE_AN_ACTION || e == TurnManagingControllerEvents.SHOWING_POSSIBLE_PLAYER_POSSIBLE_ACTIONS
                    ||  e == TurnManagingControllerEvents.INFORMING_PLAYER_WIN || e== TurnManagingControllerEvents.INFORMING_PLAYER_LOSS){
                currentState = TurnMangingControllerStates.WAITING_FOR_A_PLAYER_RESPONSE_STATE;
                currentState.receivingAPlayerResponseStrategy();
                currentState.receivingAPlayerConfirmationStrategy();
            }

        }

        public void setCurrentState(TurnMangingControllerStates currentState) {
            this.currentState = currentState;
        }

        public TurnMangingControllerStates getCurrentState() {
            return currentState;
        }
    }



    @Override
    public void run() {

        while(true){


            for(String s: usernames){


                currentPlayer = s;

                while(currentTurn){


                    ControllerMessage showActions = new ControllerMessage(MessageTypes.INFORMATION, Events.SHOW_POSSIBLE_ACTIONS, MessageSender.TURN_MANAGING_CONTROLLER);
                    showActions.setPlayerUsername(currentPlayer);
                    turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
                    notify(showActions);
                    turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.SHOWING_POSSIBLE_PLAYER_POSSIBLE_ACTIONS);

                    if (model.hasPlayerLost(model.getPlayer(currentPlayer))) {

                        ControllerMessage lossMessage = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.LOSS, MessageSender.TURN_MANAGING_CONTROLLER);
                        lossMessage.setPlayerUsername(currentPlayer);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
                        notify(lossMessage);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.INFORMING_PLAYER_LOSS);

                        currentTurn = false;
                    }


                    if(currentTurn){
                        ControllerMessage makeAction = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.ASK_TO_SELECT_ACTION, MessageSender.TURN_MANAGING_CONTROLLER);
                        makeAction.setPlayerUsername(currentPlayer);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
                        notify(makeAction);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.ASKING_PLAYER_TO_MAKE_AN_ACTION);
                    }


                    if (model.isTurnEnded(model.getPlayer(currentPlayer))) {
                        model.proceedNexTurn(model.getPlayer(currentPlayer));
                        currentTurn = false;
                    }


                    else if(model.hasPlayerJustMoved(model.getPlayer(currentPlayer)) && (model.hasPlayerWon(model.getPlayer(currentPlayer)))) {

                        ControllerMessage winMessage = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.WIN, MessageSender.TURN_MANAGING_CONTROLLER);
                        winMessage.setPlayerUsername(currentPlayer);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
                        notify(winMessage);
                        turnManagingControllerStateMachine.triggeringEvent(TurnManagingControllerEvents.INFORMING_PLAYER_WIN);

                    }

                }

                currentTurn = true;
            }



        }



    }









    /**Messages sent by the clients and forwarded by the playerResponse's virtual view*/

    @Override
    public void update(ClientMessage message) {

        if(message.getMessageSender() == MessageSender.TURN_MANAGING_CONTROLLER){
            if(message.getMessageType() == MessageTypes.RESPONSE){

                switch(message.getMessageEvent()){

                    case SHOW_POSSIBLE_ACTIONS:
                        responseLock.unLock();
                        model.showActions(model.getPlayer(currentPlayer));
                        break;

                    case ASK_TO_SELECT_ACTION:
                        responseLock.unLock();
                        model.performAction(model.getPlayer(currentPlayer), message.getSelectedAction());
                        break;


                    case WIN:
                        responseLock.unLock();
                        model.endGame();
                        break;

                    case LOSS:
                        responseLock.unLock();
                        model.rollbackTurnActions(model.getPlayer(currentPlayer), true);
                        model.removePlayer(model.getPlayer(currentPlayer));
                        //TODO crasha con null qua
                        break;
                }
            }

            else if(message.getMessageType() == MessageTypes.INFORMATION){
                confirmLock.unLock();
            }
        }

    }
}

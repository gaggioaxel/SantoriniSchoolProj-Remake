package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.shared.message.*;
import it.polimi.ingsw.shared.socket.Client;
import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.shared.utils.CircularList;


import java.util.Random;





/**The setupController is responsible for the setup portion of the game: it chooses the challenger randomly,
 * notifies the latter, asks to choose the deck, asks every player to pick a card, asks the challenger to
 * choose the first player, asks every player to pick a color and finally asks every player to place the 2 workers.
 * Every response is forwarded to the model which gets updated.
 *
 * It doesn't communicate directly with the client, instead it communicates with the virtualView which receives events
 * from the client through the network and vice versa.
 *
 *
 * It is observer of the virtual view's PlayerResponse and observable to the Virtual View's
 * ControllerMessageSender.
 */





public class SetupController extends Observable<ControllerMessage> implements Runnable, Observer<ClientMessage> {

    private final Controller controller;

    private final Model model;

    private final CircularList<VirtualView> virtualViews;

    private final CircularList<String> usernames;

    private String challenger;

    private static int numPlayers;

    private String firstPlayer;

    private String currentPlayer;


    private final SetupControllerStateMachine setupControllerStateMachine;


    private static Client.Unlock responseLock;
    private static Client.Unlock confirmLock;






    /**
     * SetupController's constructor
     * @param c controller
     * @param m model
     * @param names players' usernames
     * @param vViews players' virtualViews
     * @param n number of players
     */


    public SetupController(Controller c, Model m, CircularList<String> names, CircularList<VirtualView> vViews, int n) {
        this.controller = c;
        this.model = m;
        this.virtualViews = vViews;
        this.usernames = names;

        numPlayers = n;

        this.setupControllerStateMachine = new SetupControllerStateMachine();
        responseLock = new Client.Unlock();
        confirmLock = new Client.Unlock();
    }







    /**
     * Events that happen during the setup part of the game
     */

    public enum SetupControllerEvents {
        NOTIFY_INFORMATION_TO_EVERY_PLAYER,
        NOTIFY_REQUEST_TO_ONE_PLAYER,
        INFORMING_CHALLENGER_STATUS,
        ASKING_THE_CHALLENGER_TO_CHOOSE_THE_FIRST_PLAYER,
        ASKING_CHALLENGER_TO_CHOOSE_DECK,
        ASKING_PLAYER_TO_PICK_A_CARD,
        ASKING_PLAYER_TO_PICK_A_COLOR,
        ASKING_PLAYER_TO_PLACE_WORKERS,
        UPDATING_AVAILABLE_POSITIONS,
        SENDING_WORKER_DETAILS

    }





    /**Strategies that are used by the setupControllerStateMachine based on the event*/

    public interface PostEventStrategies {
        void notifyInformationToEveryPlayerSStrategy();
        void notifyRequestToOnePlayerStrategy();
        void receivingAPlayerResponseStrategy();
        void receivingAPlayerConfirmationStrategy();

    }







    /**States which the setupControllerStateMachine can be at based on the occurring events.
     * They determine the implementation of the different locks which work on the game's
     * synchronization*/

    public enum SetupControllerStates implements PostEventStrategies {

        SETTING_NOTIFY_INFORMATION_TO_EVERY_PLAYER_STATUS_STATE{

            @Override

            public void notifyInformationToEveryPlayerSStrategy() {
                confirmLock.reset();
                confirmLock.setLatch(numPlayers);
            }

            @Override
            public void notifyRequestToOnePlayerStrategy() {

            }

            @Override
            public void receivingAPlayerResponseStrategy() {

            }

            @Override
            public void receivingAPlayerConfirmationStrategy() {

            }

        },

        WAITING_FOR_EVERY_PLAYER_TO_RECEIVE_INFORMATION_STATE{
            @Override
            public void notifyInformationToEveryPlayerSStrategy() {

            }

            @Override
            public void notifyRequestToOnePlayerStrategy() {

            }

            @Override
            public void receivingAPlayerResponseStrategy() {

            }

            @Override
            public void receivingAPlayerConfirmationStrategy() {
                try {
                    confirmLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        },

        SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE{
            @Override
            public void notifyInformationToEveryPlayerSStrategy() {

            }

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

        WAITING_FOR_A_PLAYER_RESPONSE_STATE {
            @Override
            public void notifyInformationToEveryPlayerSStrategy() {

            }

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







    /**the controllerStateMachine is held accountable for the game's setup flow*/

    public static class SetupControllerStateMachine {

        private SetupControllerStates currentState;

        public void triggeringEvent(SetupControllerEvents e) {

            if(e == SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER){
                currentState = SetupControllerStates.SETTING_NOTIFY_INFORMATION_TO_EVERY_PLAYER_STATUS_STATE;
                currentState.notifyInformationToEveryPlayerSStrategy();
            }

            else if(e == SetupControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER){
                currentState = SetupControllerStates.SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE;
                currentState.notifyRequestToOnePlayerStrategy();
            }


            else if (e == SetupControllerEvents.INFORMING_CHALLENGER_STATUS || e == SetupControllerEvents.ASKING_THE_CHALLENGER_TO_CHOOSE_THE_FIRST_PLAYER) {
                currentState = SetupControllerStates.WAITING_FOR_EVERY_PLAYER_TO_RECEIVE_INFORMATION_STATE;
                currentState.receivingAPlayerConfirmationStrategy();
            }

            else if(e == SetupControllerEvents.ASKING_CHALLENGER_TO_CHOOSE_DECK||e == SetupControllerEvents.ASKING_PLAYER_TO_PICK_A_CARD ||
                    e == SetupControllerEvents.ASKING_PLAYER_TO_PICK_A_COLOR || e ==SetupControllerEvents.ASKING_PLAYER_TO_PLACE_WORKERS ||
                    e == SetupControllerEvents.UPDATING_AVAILABLE_POSITIONS|| e == SetupControllerEvents.SENDING_WORKER_DETAILS) {
                currentState = SetupControllerStates.WAITING_FOR_A_PLAYER_RESPONSE_STATE;
                currentState.receivingAPlayerResponseStrategy();
                currentState.receivingAPlayerConfirmationStrategy();
            }


        }

        public void setCurrentState(SetupControllerStates currentState) {
            this.currentState = currentState;
        }

        public SetupControllerStates getCurrentState() {
            return currentState;
        }
    }







    @Override
    public void run() {

        for(int i=0; i < virtualViews.size(); i++){
            this.addObserver(virtualViews.get(i).getControllerMessageSender());
            virtualViews.get(i).getPlayerResponse().addObserver(this);
            virtualViews.get(i).setUsername(usernames.get(i));
        }




        int chaIndex = getChallenger(usernames);

        challenger = usernames.get(chaIndex);





        ControllerMessage challengerMessage = new ControllerMessage(MessageTypes.INFORMATION, Events.CHALLENGER, MessageSender.SETUP_CONTROLLER);
        challengerMessage.setChallenger(challenger);
        challengerMessage.setNumbPlayers(numPlayers);
        challengerMessage.setPlayersInGame(usernames);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);
        notify(challengerMessage);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.INFORMING_CHALLENGER_STATUS);






        ControllerMessage deckMessage = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.DECK, MessageSender.SETUP_CONTROLLER);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
        notify(deckMessage);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.ASKING_CHALLENGER_TO_CHOOSE_DECK);



        /**the new order is set: the challenger gets asked about which card to pick last*/


        usernames.setFirst(chaIndex+1);


        for(String username: usernames) {
            ControllerMessage cardMessage = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.CARD, MessageSender.SETUP_CONTROLLER);
            currentPlayer = username;
            cardMessage.setPlayerUsername(currentPlayer);
            setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
            notify(cardMessage);
            setupControllerStateMachine.triggeringEvent(SetupControllerEvents.ASKING_PLAYER_TO_PICK_A_CARD);
        }






        ControllerMessage firstPlayerMessage = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.FIRST_PLAYER, MessageSender.SETUP_CONTROLLER);
        firstPlayerMessage.setPlayersInGame(usernames);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);
        notify(firstPlayerMessage);
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.ASKING_THE_CHALLENGER_TO_CHOOSE_THE_FIRST_PLAYER);






        /**the newest order is set*/

        for(int i=0; i <usernames.size(); i++){
            if(usernames.get(i).equals(firstPlayer)){
                usernames.setFirst(i);
            }
        }




        for(String s: usernames) {
            ControllerMessage colorMessage = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.COLOR, MessageSender.SETUP_CONTROLLER);
            currentPlayer=s;
            colorMessage.setPlayerUsername(currentPlayer);
            setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
            notify(colorMessage);
            setupControllerStateMachine.triggeringEvent(SetupControllerEvents.ASKING_PLAYER_TO_PICK_A_COLOR);
        }






        model.showAvailablePlacesForWorkersPos();
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);
        model.showUpdatedBoard("SetupController");
        setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);







        for(String s: usernames) {

            currentPlayer=s;
            int i=0;
            while(i <=1){

                ControllerMessage placeWorkerMessage = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.PLACE_WORKER, MessageSender.SETUP_CONTROLLER);
                placeWorkerMessage.setPlayerUsername(currentPlayer);
                setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
                notify(placeWorkerMessage);
                setupControllerStateMachine.triggeringEvent(SetupControllerEvents.ASKING_PLAYER_TO_PLACE_WORKERS);

                model.showUpdatedBoard("SetupController");

                model.sendWorkerDetails(model.getPlayer(currentPlayer));
                setupControllerStateMachine.triggeringEvent(SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);


                i++;
            }
        }

        controller.setUsernames(usernames);
        controller.unlockTurnLock();

    }





    /**
     * the challenger is chosen randomly
     * @param players
     * @return index of the element of the circularList which represents the challenger
     */

    public int getChallenger(CircularList<String> players) {
        return new Random().nextInt(players.size());

    }








    /**Messages sent by the clients and forwarded by the playerResponse's virtual view
     * If the MessageType is RESPONSE, the controller forwards the message to the model
     */

    @Override
    public void update(ClientMessage message) {

        if(message.getMessageSender() == MessageSender.SETUP_CONTROLLER){
            if(message.getMessageType() == MessageTypes.RESPONSE) {

                switch (message.getMessageEvent()) {

                    case DECK:
                        responseLock.unLock();
                        model.selectCardsFromDeck(message.getHand());
                        break;

                    case CARD:
                        responseLock.unLock();
                        model.playerPicksACard(model.getPlayer(currentPlayer), message.getCard() );
                        break;

                    case FIRST_PLAYER:
                        confirmLock.unLock();
                        firstPlayer = message.getFirstPlayer();

                        break;


                    case COLOR:
                        responseLock.unLock();
                        model.setPlayerColor(model.getPlayer(currentPlayer), message.getColor());
                        break;

                    case PLACE_WORKER:
                        responseLock.unLock();
                        model.placeWorker(model.getPlayer(currentPlayer), message.getSelectedPosition());
                        break;


                }

            }

            else if(message.getMessageType() == MessageTypes.INFORMATION){
                confirmLock.unLock();
            }

        }


    }


}



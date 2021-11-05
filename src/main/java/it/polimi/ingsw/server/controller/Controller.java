package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.shared.socket.Client;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.shared.message.*;
import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.shared.utils.CircularList;


import java.io.IOException;
import java.net.Socket;





/**The Controller is responsible of the first interactions between the server and client. It asks the
 * first connected player the chosen username, the number of players that he/she wants to have a match with and
 * communicates the responses to the model. It then asks the other player/s their username and forwards them to the model.
 * It doesn't communicate directly with the client, instead it communicates with the virtualView which receives events
 * from the client through the network and vice versa.
 *
 *
 * It is observer of the virtual view's PlayerResponse and observable to the Virtual View's
 * ControllerMessageSender.
 */





public class Controller extends Observable<ControllerMessage> implements Observer<ClientMessage> {

    private final Server server;

    private final Model model;



    private final ControllerStateMachine controllerStateMachine;

    private CircularList<String> usernames;

    private final CircularList<VirtualView> virtualViews;



    private static Client.Unlock confirmLock;

    private static Client.Unlock responseLock;

    private final Client.Unlock setupLock;

    private final Client.Unlock turnLock;



    private SetupController setupController;

    private TurnManagingController turnManagingController;


    private int numbPlayers = 0;

    private static int numbPlayersCounter;




    /**
     * Controller's constructor
     * @param server
     */

    public Controller(Server server) {
        this.server = server;
        this.model = new Model();
        this.virtualViews = new CircularList<>();


        this.usernames = new CircularList<>();


        this.controllerStateMachine = new ControllerStateMachine();

        confirmLock = new Client.Unlock();
        setupLock = new Client.Unlock();
        responseLock = new Client.Unlock();
        turnLock = new Client.Unlock();
    }





    /**Events which happen at the beginning phase of the game*/

    public enum ControllerEvents{
        NOTIFY_REQUEST_TO_ONE_PLAYER,
        ASKING__USERNAME,
        ASKING_NUMBER_OF_PLAYERS,
    }






    /**
     * Strategies that are used by the controllerStateMachine based on the event
     */

    public interface EventStrategies{
        void notifyRequestToOnePlayerStrategy();
        void receivingAPlayerResponseStrategy();
        void receivingPlayerConfirmationStrategy();
    }






    /**States which the controllerStateMachine can be at based on the occurring events.
     * They determine the implementation of the different locks which work on the game's
     * synchronization*/


    public enum ControllerStates implements EventStrategies{

        SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE {
            @Override
            public void notifyRequestToOnePlayerStrategy() {
                confirmLock.reset();
                responseLock.reset();
                confirmLock.setLatch(numbPlayersCounter);
            }

            @Override
            public void receivingAPlayerResponseStrategy() {

            }

            @Override
            public void receivingPlayerConfirmationStrategy() {

            }
        },

        WAITING_FOR_A_PLAYER_RESPONSE {
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
            public void receivingPlayerConfirmationStrategy() {
                try{
                    confirmLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }






    /**
     * the ControllerStateMachine is held accountable for the beginning of the game's flow.
     **/


    public static class ControllerStateMachine{

        private ControllerStates currentState;


        public void triggeringEvent(ControllerEvents e){
            if(e == ControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER){
                currentState = ControllerStates.SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE;
                currentState.notifyRequestToOnePlayerStrategy();
            }


            else if(e == ControllerEvents.ASKING__USERNAME){
                currentState = ControllerStates.WAITING_FOR_A_PLAYER_RESPONSE;
                currentState.receivingAPlayerResponseStrategy();
                currentState.receivingPlayerConfirmationStrategy();

            }

            else if(e == ControllerEvents.ASKING_NUMBER_OF_PLAYERS){
                currentState = ControllerStates.WAITING_FOR_A_PLAYER_RESPONSE;
                currentState.receivingAPlayerResponseStrategy();
                currentState.receivingPlayerConfirmationStrategy();
            }

        }

        public void setCurrentState(ControllerStates currentState) {
            this.currentState = currentState;
        }

        public ControllerStates getCurrentState() {
            return currentState;
        }
    }







    /**Controller's main which activates the setupController and turnManagingController*/

    public void main() {

        try {
            setupLock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setupController = new SetupController(this,model, usernames, virtualViews, numbPlayers);

        Thread s = new Thread(setupController);
        s.start();


        try{
            turnLock.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        this.turnManagingController = new TurnManagingController(this, model, usernames, virtualViews, numbPlayers );

        Thread t = new Thread(turnManagingController);
        t.start();

    }






    /**
     * This method asks the first player the chosen username and the number of players that are going to participate in the
     * current match. A virtual view is associated to the player.
     * @param socket of the firstPlayer
     * @return the number of players
     * @throws IOException
     */


    public int addFirstPlayer(Socket socket) throws IOException {

        numbPlayersCounter = 1;

        VirtualView firstVirtualView = new VirtualView(this, socket);
        virtualViews.add(firstVirtualView);
        this.addObserver(firstVirtualView.getControllerMessageSender());
        model.addObserver(firstVirtualView.getModelMessageSender());


        ControllerMessage askUserN = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.USERNAME, MessageSender.CONTROLLER);
        askUserN.setPlayersInGame(usernames);
        controllerStateMachine.triggeringEvent(ControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
        notify(askUserN);
        controllerStateMachine.triggeringEvent(ControllerEvents.ASKING__USERNAME);


        ControllerMessage askNumPlayers = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.NUMB_PLAYERS, MessageSender.CONTROLLER);
        controllerStateMachine.triggeringEvent(ControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
        notify(askNumPlayers);
        controllerStateMachine.triggeringEvent(ControllerEvents.ASKING_NUMBER_OF_PLAYERS);



        return numbPlayers;

    }






    /**
     * This method asks the different players their username and to each and of them a virtualView is made
     * @param socket of player
     * @throws IOException
     */


    public void addPlayer(Socket socket) throws IOException {

        numbPlayersCounter++;

        VirtualView newVView = new VirtualView(this, socket);
        virtualViews.add(newVView);
        this.addObserver(newVView.getControllerMessageSender());
        model.addObserver(newVView.getModelMessageSender());

        ControllerMessage askUserN = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.USERNAME, MessageSender.CONTROLLER);
        askUserN.setPlayersInGame(usernames);
        controllerStateMachine.triggeringEvent(ControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);
        notify(askUserN);
        controllerStateMachine.triggeringEvent(ControllerEvents.ASKING__USERNAME);


        if (numbPlayersCounter == numbPlayers) {
            setupLock.unLock();
        }


    }







    /**Messages sent by the clients and forwarded by the playerResponse's virtual view*/

    @Override
    public void update(ClientMessage message) {

        if(message.getMessageSender() == MessageSender.CONTROLLER){
            if(message.getMessageType() == MessageTypes.RESPONSE) {

                switch (message.getMessageEvent()) {

                    case USERNAME:
                        usernames.add(message.getUsername());
                        responseLock.unLock();
                        model.setUsername(message.getUsername());


                        break;

                    case NUMB_PLAYERS:

                        if (message.getNumbPlayers() == 2) {
                            numbPlayers = 2;
                        } else if (message.getNumbPlayers() == 3) {
                            numbPlayers = 3;
                        }

                        responseLock.unLock();
                        model.setNumPlayer(numbPlayers);

                        break;

                }

            }

            else if(message.getMessageType() == MessageTypes.INFORMATION){
                confirmLock.unLock();
            }

        }


    }



    public void setUsernames(CircularList<String> names){this.usernames=names;}

    public void unlockTurnLock(){this.turnLock.unLock();}

    public Model getModel(){return this.model;}



}




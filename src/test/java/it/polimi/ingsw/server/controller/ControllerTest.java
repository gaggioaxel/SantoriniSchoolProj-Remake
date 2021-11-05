package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.shared.message.*;
import it.polimi.ingsw.shared.socket.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;




public class ControllerTest {

    private Controller controller;
    private Server server;
    private Controller.ControllerStateMachine controllerStateMachine;
    private static Client.Unlock confirmLock;
    private static Client.Unlock responseLock;
    private Client.Unlock setupLock;
    private Client.Unlock turnLock;
    private SetupController setupController;
    private TurnManagingController turnManagingController;
    private static int numbPlayersCounter;


    @BeforeEach

    public void setUp(){
        controller = new Controller(server);
        controllerStateMachine = new Controller.ControllerStateMachine();
        confirmLock = new Client.Unlock();
        setupLock = new Client.Unlock();
        responseLock = new Client.Unlock();
        turnLock = new Client.Unlock();
    }



    @Test

    public void CheckUsernameRequest() {


        ControllerMessage cMessage1 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.USERNAME, MessageSender.CONTROLLER);

        controllerStateMachine.triggeringEvent(Controller.ControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);

        controllerStateMachine.setCurrentState(Controller.ControllerStates.SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE);



        assertEquals(MessageTypes.SETUP_REQUEST, cMessage1.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, cMessage1.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, cMessage1.getMessageType());
        assertNotEquals(MessageTypes.ERROR, cMessage1.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, cMessage1.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, cMessage1.getMessageType());



        assertEquals(Events.USERNAME, cMessage1.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, cMessage1.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, cMessage1.getMessageEvent());
        assertNotEquals(Events.DECK, cMessage1.getMessageEvent());
        assertNotEquals(Events.COLOR, cMessage1.getMessageEvent());
        assertNotEquals(Events.CARD, cMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER, cMessage1.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, cMessage1.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, cMessage1.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, cMessage1.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, cMessage1.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, cMessage1.getMessageEvent());
        assertNotEquals(Events.WIN, cMessage1.getMessageEvent());
        assertNotEquals(Events.LOSS, cMessage1.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, cMessage1.getMessageEvent());
        assertNotEquals(Events.END_GAME, cMessage1.getMessageEvent());
        assertNotEquals(Events.BOARD, cMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, cMessage1.getMessageEvent());




        assertNotEquals(MessageSender.MODEL, cMessage1.getMessageSender());
        assertEquals(MessageSender.CONTROLLER, cMessage1.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, cMessage1.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, cMessage1.getMessageSender());


    }




    @Test

    public void checkNumbOfPlayersRequest() {

        ControllerMessage cMessage2 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.NUMB_PLAYERS, MessageSender.CONTROLLER);



        assertEquals(MessageTypes.SETUP_REQUEST, cMessage2.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, cMessage2.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, cMessage2.getMessageType());
        assertNotEquals(MessageTypes.ERROR, cMessage2.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, cMessage2.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, cMessage2.getMessageType());




        assertNotEquals(Events.USERNAME, cMessage2.getMessageEvent());
        assertEquals(Events.NUMB_PLAYERS, cMessage2.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, cMessage2.getMessageEvent());
        assertNotEquals(Events.DECK, cMessage2.getMessageEvent());
        assertNotEquals(Events.COLOR, cMessage2.getMessageEvent());
        assertNotEquals(Events.CARD, cMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER, cMessage2.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, cMessage2.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, cMessage2.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, cMessage2.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, cMessage2.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, cMessage2.getMessageEvent());
        assertNotEquals(Events.WIN, cMessage2.getMessageEvent());
        assertNotEquals(Events.LOSS, cMessage2.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, cMessage2.getMessageEvent());
        assertNotEquals(Events.END_GAME, cMessage2.getMessageEvent());
        assertNotEquals(Events.BOARD, cMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, cMessage2.getMessageEvent());



        assertNotEquals(MessageSender.MODEL, cMessage2.getMessageSender());
        assertEquals(MessageSender.CONTROLLER, cMessage2.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, cMessage2.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, cMessage2.getMessageSender());
    }





    @Test

    public void checkUsernameResponse(){

        ClientMessage cMessage3 = new ClientMessage(MessageTypes.RESPONSE, Events.USERNAME,MessageSender.CONTROLLER);


        assertNotEquals(MessageTypes.SETUP_REQUEST, cMessage3.getMessageType());
        assertEquals(MessageTypes.RESPONSE, cMessage3.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, cMessage3.getMessageType());
        assertNotEquals(MessageTypes.ERROR, cMessage3.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, cMessage3.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, cMessage3.getMessageType());




        assertEquals(Events.USERNAME, cMessage3.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, cMessage3.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, cMessage3.getMessageEvent());
        assertNotEquals(Events.DECK, cMessage3.getMessageEvent());
        assertNotEquals(Events.COLOR, cMessage3.getMessageEvent());
        assertNotEquals(Events.CARD, cMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER, cMessage3.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, cMessage3.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, cMessage3.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, cMessage3.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, cMessage3.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, cMessage3.getMessageEvent());
        assertNotEquals(Events.WIN, cMessage3.getMessageEvent());
        assertNotEquals(Events.LOSS, cMessage3.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, cMessage3.getMessageEvent());
        assertNotEquals(Events.END_GAME, cMessage3.getMessageEvent());
        assertNotEquals(Events.BOARD, cMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, cMessage3.getMessageEvent());




        assertNotEquals(MessageSender.MODEL, cMessage3.getMessageSender());
        assertEquals(MessageSender.CONTROLLER, cMessage3.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, cMessage3.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, cMessage3.getMessageSender());



    }



    @Test

    public void checkNumbPlayerResponse(){

        ClientMessage cMessage4 = new ClientMessage(MessageTypes.RESPONSE, Events.NUMB_PLAYERS,MessageSender.CONTROLLER);



        assertNotEquals(MessageTypes.SETUP_REQUEST, cMessage4.getMessageType());
        assertEquals(MessageTypes.RESPONSE, cMessage4.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, cMessage4.getMessageType());
        assertNotEquals(MessageTypes.ERROR, cMessage4.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, cMessage4.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, cMessage4.getMessageType());



        assertNotEquals(Events.USERNAME, cMessage4.getMessageEvent());
        assertEquals(Events.NUMB_PLAYERS, cMessage4.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, cMessage4.getMessageEvent());
        assertNotEquals(Events.DECK, cMessage4.getMessageEvent());
        assertNotEquals(Events.COLOR, cMessage4.getMessageEvent());
        assertNotEquals(Events.CARD, cMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER, cMessage4.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, cMessage4.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, cMessage4.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, cMessage4.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, cMessage4.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, cMessage4.getMessageEvent());
        assertNotEquals(Events.WIN, cMessage4.getMessageEvent());
        assertNotEquals(Events.LOSS, cMessage4.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, cMessage4.getMessageEvent());
        assertNotEquals(Events.END_GAME, cMessage4.getMessageEvent());
        assertNotEquals(Events.BOARD, cMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, cMessage4.getMessageEvent());




        assertNotEquals(MessageSender.MODEL, cMessage4.getMessageSender());
        assertEquals(MessageSender.CONTROLLER, cMessage4.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, cMessage4.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, cMessage4.getMessageSender());

    }



}


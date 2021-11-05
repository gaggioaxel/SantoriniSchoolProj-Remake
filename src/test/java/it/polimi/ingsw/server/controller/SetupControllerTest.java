package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.shared.message.*;
import it.polimi.ingsw.shared.utils.CircularList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class SetupControllerTest {

    private Controller controller;
    private SetupController setupController;
    private final CircularList<VirtualView> virtualViews = new CircularList<>();
    private final CircularList<String> usernames = new CircularList<>();
    private int numPlayers;
    private Model model;
    private String firstPlayer;
    private String currentPlayer;
    private SetupController.SetupControllerStateMachine setupControllerStateMachine;




    @BeforeEach

    public void setUp(){
        setupController = new SetupController(controller, model, usernames, virtualViews, numPlayers);
        setupControllerStateMachine = new SetupController.SetupControllerStateMachine();
    }




    @Test

    public void checkChallengerRequest(){

        ControllerMessage sMessage1 = new ControllerMessage(MessageTypes.INFORMATION, Events.CHALLENGER, MessageSender.SETUP_CONTROLLER);

        setupControllerStateMachine.triggeringEvent(SetupController.SetupControllerEvents.NOTIFY_INFORMATION_TO_EVERY_PLAYER);

        setupControllerStateMachine.setCurrentState(SetupController.SetupControllerStates.SETTING_NOTIFY_INFORMATION_TO_EVERY_PLAYER_STATUS_STATE);


        assertNotEquals(MessageTypes.SETUP_REQUEST, sMessage1.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage1.getMessageType());
        assertEquals(MessageTypes.INFORMATION, sMessage1.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage1.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage1.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage1.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage1.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage1.getMessageEvent());
        assertEquals(Events.CHALLENGER, sMessage1.getMessageEvent());
        assertNotEquals(Events.DECK, sMessage1.getMessageEvent());
        assertNotEquals(Events.COLOR, sMessage1.getMessageEvent());
        assertNotEquals(Events.CARD, sMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage1.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, sMessage1.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, sMessage1.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, sMessage1.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage1.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage1.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage1.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage1.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage1.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage1.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage1.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage1.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage1.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage1.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage1.getMessageSender());



    }

    @Test

    public void checkDeckRequest(){

        ControllerMessage sMessage2 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.DECK, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, sMessage2.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage2.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, sMessage2.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage2.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage2.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage2.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage2.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage2.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, sMessage2.getMessageEvent());
        assertEquals(Events.DECK, sMessage2.getMessageEvent());
        assertNotEquals(Events.COLOR, sMessage2.getMessageEvent());
        assertNotEquals(Events.CARD, sMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage2.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, sMessage2.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, sMessage2.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, sMessage2.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage2.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage2.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage2.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage2.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage2.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage2.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage2.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage2.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage2.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage2.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage2.getMessageSender());


    }


    @Test

    public void checkCardRequest(){

        ControllerMessage sMessage3 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.CARD, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, sMessage3.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage3.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, sMessage3.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage3.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage3.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage3.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage3.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage3.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, sMessage3.getMessageEvent());
        assertNotEquals(Events.DECK, sMessage3.getMessageEvent());
        assertNotEquals(Events.COLOR, sMessage3.getMessageEvent());
        assertEquals(Events.CARD, sMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage3.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, sMessage3.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, sMessage3.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,sMessage3.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage3.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage3.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage3.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage3.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage3.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage3.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage3.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage3.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage3.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage3.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage3.getMessageSender());
    }

    @Test

    public void checkFirstPlayerRequest(){

        ControllerMessage sMessage4 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.FIRST_PLAYER, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, sMessage4.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage4.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, sMessage4.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage4.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage4.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage4.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage4.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage4.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, sMessage4.getMessageEvent());
        assertNotEquals(Events.DECK, sMessage4.getMessageEvent());
        assertNotEquals(Events.COLOR, sMessage4.getMessageEvent());
        assertNotEquals(Events.CARD, sMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage4.getMessageEvent());
        assertEquals(Events.FIRST_PLAYER, sMessage4.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, sMessage4.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,sMessage4.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage4.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage4.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage4.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage4.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage4.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage4.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage4.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage4.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage4.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage4.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage4.getMessageSender());
    }



    @Test


    public void checkColorRequest(){

        ControllerMessage sMessage5 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.COLOR, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, sMessage5.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage5.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, sMessage5.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage5.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage5.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage5.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage5.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage5.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, sMessage5.getMessageEvent());
        assertNotEquals(Events.DECK, sMessage5.getMessageEvent());
        assertEquals(Events.COLOR, sMessage5.getMessageEvent());
        assertNotEquals(Events.CARD, sMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage5.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER,sMessage5.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, sMessage5.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,sMessage5.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage5.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage5.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage5.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage5.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage5.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage5.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage5.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage5.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage5.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage5.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage5.getMessageSender());
    }


    @Test

    public void checkPlaceWorkerRequest(){

        ControllerMessage sMessage6 = new ControllerMessage(MessageTypes.SETUP_REQUEST, Events.PLACE_WORKER, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, sMessage6.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, sMessage6.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, sMessage6.getMessageType());
        assertNotEquals(MessageTypes.ERROR, sMessage6.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, sMessage6.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, sMessage6.getMessageType());

        assertNotEquals(Events.USERNAME, sMessage6.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, sMessage6.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, sMessage6.getMessageEvent());
        assertNotEquals(Events.DECK, sMessage6.getMessageEvent());
        assertNotEquals(Events.COLOR, sMessage6.getMessageEvent());
        assertNotEquals(Events.CARD, sMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER, sMessage6.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER,sMessage6.getMessageEvent());
        assertEquals(Events.PLACE_WORKER, sMessage6.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,sMessage6.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, sMessage6.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, sMessage6.getMessageEvent());
        assertNotEquals(Events.WIN, sMessage6.getMessageEvent());
        assertNotEquals(Events.LOSS, sMessage6.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, sMessage6.getMessageEvent());
        assertNotEquals(Events.END_GAME, sMessage6.getMessageEvent());
        assertNotEquals(Events.BOARD, sMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, sMessage6.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, sMessage6.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, sMessage6.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, sMessage6.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, sMessage6.getMessageSender());

    }



    @Test

    public void checkChallengerResponse(){

        ClientMessage clientMessage1 = new ClientMessage(MessageTypes.RESPONSE, Events.CHALLENGER, MessageSender.SETUP_CONTROLLER);


        assertNotEquals(MessageTypes.SETUP_REQUEST, clientMessage1.getMessageType());
        assertEquals(MessageTypes.RESPONSE, clientMessage1.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage1.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage1.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage1.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage1.getMessageType());


        assertNotEquals(Events.USERNAME, clientMessage1.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage1.getMessageEvent());
        assertEquals(Events.CHALLENGER, clientMessage1.getMessageEvent());
        assertNotEquals(Events.DECK, clientMessage1.getMessageEvent());
        assertNotEquals(Events.COLOR, clientMessage1.getMessageEvent());
        assertNotEquals(Events.CARD, clientMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage1.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, clientMessage1.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, clientMessage1.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, clientMessage1.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage1.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage1.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage1.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage1.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage1.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage1.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage1.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage1.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage1.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage1.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage1.getMessageSender());

    }

    @Test

    public void checkDeckResponse(){

        ClientMessage clientMessage2 = new ClientMessage(MessageTypes.RESPONSE, Events.DECK, MessageSender.SETUP_CONTROLLER);


        assertNotEquals(MessageTypes.SETUP_REQUEST, clientMessage2.getMessageType());
        assertEquals(MessageTypes.RESPONSE, clientMessage2.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage2.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage2.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage2.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage2.getMessageType());


        assertNotEquals(Events.USERNAME, clientMessage2.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage2.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, clientMessage2.getMessageEvent());
        assertEquals(Events.DECK, clientMessage2.getMessageEvent());
        assertNotEquals(Events.COLOR, clientMessage2.getMessageEvent());
        assertNotEquals(Events.CARD, clientMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage2.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, clientMessage2.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, clientMessage2.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, clientMessage2.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage2.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage2.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage2.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage2.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage2.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage2.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage2.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage2.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage2.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage2.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage2.getMessageSender());
    }



    @Test

    public void checkCardResponse(){

        ClientMessage clientMessage3 = new ClientMessage(MessageTypes.RESPONSE, Events.CARD, MessageSender.SETUP_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, clientMessage3.getMessageType());
        assertEquals(MessageTypes.RESPONSE, clientMessage3.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage3.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage3.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage3.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage3.getMessageType());

        assertNotEquals(Events.USERNAME, clientMessage3.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage3.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, clientMessage3.getMessageEvent());
        assertNotEquals(Events.DECK, clientMessage3.getMessageEvent());
        assertNotEquals(Events.COLOR, clientMessage3.getMessageEvent());
        assertEquals(Events.CARD, clientMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage3.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, clientMessage3.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, clientMessage3.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,clientMessage3.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage3.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage3.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage3.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage3.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage3.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage3.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage3.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage3.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage3.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage3.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage3.getMessageSender());

    }


    @Test

    public void checkFirstPlayerResponse(){

        ClientMessage clientMessage4 = new ClientMessage(MessageTypes.SETUP_REQUEST, Events.FIRST_PLAYER, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, clientMessage4.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, clientMessage4.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage4.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage4.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage4.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage4.getMessageType());

        assertNotEquals(Events.USERNAME, clientMessage4.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage4.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, clientMessage4.getMessageEvent());
        assertNotEquals(Events.DECK, clientMessage4.getMessageEvent());
        assertNotEquals(Events.COLOR, clientMessage4.getMessageEvent());
        assertNotEquals(Events.CARD, clientMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage4.getMessageEvent());
        assertEquals(Events.FIRST_PLAYER, clientMessage4.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, clientMessage4.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,clientMessage4.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage4.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage4.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage4.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage4.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage4.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage4.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage4.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage4.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage4.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage4.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage4.getMessageSender());

    }


    @Test

    public void checkColorResponse(){

        ClientMessage clientMessage5 = new ClientMessage(MessageTypes.SETUP_REQUEST, Events.COLOR, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, clientMessage5.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, clientMessage5.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage5.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage5.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage5.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage5.getMessageType());

        assertNotEquals(Events.USERNAME, clientMessage5.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage5.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, clientMessage5.getMessageEvent());
        assertNotEquals(Events.DECK, clientMessage5.getMessageEvent());
        assertEquals(Events.COLOR, clientMessage5.getMessageEvent());
        assertNotEquals(Events.CARD, clientMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage5.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER,clientMessage5.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, clientMessage5.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,clientMessage5.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage5.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage5.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage5.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage5.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage5.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage5.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage5.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage5.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage5.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage5.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage5.getMessageSender());
    }


    @Test

    public void checkPlaceWorkerResponse(){

        ClientMessage clientMessage6 = new ClientMessage(MessageTypes.SETUP_REQUEST, Events.PLACE_WORKER, MessageSender.SETUP_CONTROLLER);

        assertEquals(MessageTypes.SETUP_REQUEST, clientMessage6.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, clientMessage6.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, clientMessage6.getMessageType());
        assertNotEquals(MessageTypes.ERROR, clientMessage6.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, clientMessage6.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, clientMessage6.getMessageType());

        assertNotEquals(Events.USERNAME, clientMessage6.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, clientMessage6.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, clientMessage6.getMessageEvent());
        assertNotEquals(Events.DECK, clientMessage6.getMessageEvent());
        assertNotEquals(Events.COLOR, clientMessage6.getMessageEvent());
        assertNotEquals(Events.CARD, clientMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER, clientMessage6.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER,clientMessage6.getMessageEvent());
        assertEquals(Events.PLACE_WORKER, clientMessage6.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION,clientMessage6.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, clientMessage6.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, clientMessage6.getMessageEvent());
        assertNotEquals(Events.WIN, clientMessage6.getMessageEvent());
        assertNotEquals(Events.LOSS, clientMessage6.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, clientMessage6.getMessageEvent());
        assertNotEquals(Events.END_GAME, clientMessage6.getMessageEvent());
        assertNotEquals(Events.BOARD, clientMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, clientMessage6.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, clientMessage6.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, clientMessage6.getMessageSender());
        assertEquals(MessageSender.SETUP_CONTROLLER, clientMessage6.getMessageSender());
        assertNotEquals(MessageSender.TURN_MANAGING_CONTROLLER, clientMessage6.getMessageSender());

    }

    public boolean assertContainsUsernames() {
        return true;
    }


    @Test

    public void checkUsernames(){

        for(String a: usernames){
            if(a.equals(currentPlayer))
                assertTrue(assertContainsUsernames());
        }

    }





}
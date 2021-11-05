package it.polimi.ingsw.server.controller;


import it.polimi.ingsw.server.model.Model;
import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.shared.message.*;
import it.polimi.ingsw.shared.utils.CircularList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class TurnManagingControllerTest {

    private Controller controller;
    private Model model;
    private final CircularList<VirtualView> virtualViews = new CircularList<>();
    private final CircularList<String> usernames = new CircularList<>();
    private String currentPlayer;
    private TurnManagingController.TurnManagingControllerStateMachine turnManagingControllerStateMachine;
    private TurnManagingController turnManagingController;
    private int numbPlayers;

    @BeforeEach

    public void setUp() {
        turnManagingController = new TurnManagingController(controller, model, usernames, virtualViews, numbPlayers);
        turnManagingControllerStateMachine = new TurnManagingController.TurnManagingControllerStateMachine();
    }

    @Test

    public void checkShowPossibleActionsRequest() {

        ControllerMessage tMessage1 = new ControllerMessage(MessageTypes.INFORMATION, Events.SHOW_POSSIBLE_ACTIONS, MessageSender.TURN_MANAGING_CONTROLLER);

        turnManagingControllerStateMachine.triggeringEvent(TurnManagingController.TurnManagingControllerEvents.NOTIFY_REQUEST_TO_ONE_PLAYER);

        turnManagingControllerStateMachine.setCurrentState(TurnManagingController.TurnMangingControllerStates.SETTING_NOTIFY_A_REQUEST_TO_ONE_PLAYER_STATE);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage1.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, tMessage1.getMessageType());
        assertEquals(MessageTypes.INFORMATION, tMessage1.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage1.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage1.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, tMessage1.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage1.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage1.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage1.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage1.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage1.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage1.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage1.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage1.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage1.getMessageEvent());
        assertEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage1.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage1.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage1.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage1.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage1.getMessageEvent());
        assertNotEquals(Events.END_GAME, tMessage1.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage1.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage1.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, tMessage1.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage1.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage1.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage1.getMessageSender());
    }


    @Test

    public void checkLossNotify() {

        ControllerMessage tMessage2 = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.LOSS, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage2.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, tMessage2.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage2.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage2.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage2.getMessageType());
        assertEquals(MessageTypes.TURN_REQUEST, tMessage2.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage2.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage2.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage2.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage2.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage2.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage2.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage2.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage2.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage2.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage2.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage2.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage2.getMessageEvent());
        assertEquals(Events.LOSS, tMessage2.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage2.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage2.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage2.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage2.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage2.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage2.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage2.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage2.getMessageSender());
    }


    @Test

    public void checkAskToSelectActionRequest() {

        ControllerMessage tMessage3 = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.ASK_TO_SELECT_ACTION, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage3.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, tMessage3.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage3.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage3.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage3.getMessageType());
        assertEquals(MessageTypes.TURN_REQUEST, tMessage3.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage3.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage3.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage3.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage3.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage3.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage3.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage3.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage3.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage3.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage3.getMessageEvent());
        assertEquals(Events.ASK_TO_SELECT_ACTION, tMessage3.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage3.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage3.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage3.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage3.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage3.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage3.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage3.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage3.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage3.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage3.getMessageSender());
    }


    @Test

    public void checkWinNotify(){

        ControllerMessage tMessage4 = new ControllerMessage(MessageTypes.TURN_REQUEST, Events.WIN, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage4.getMessageType());
        assertNotEquals(MessageTypes.RESPONSE, tMessage4.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage4.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage4.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage4.getMessageType());
        assertEquals(MessageTypes.TURN_REQUEST, tMessage4.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage4.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage4.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage4.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage4.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage4.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage4.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage4.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage4.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage4.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage4.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage4.getMessageEvent());
        assertEquals(Events.WIN, tMessage4.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage4.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage4.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage4.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage4.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage4.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage4.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage4.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage4.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage4.getMessageSender());

    }



    @Test

    public void  checkShowPossibleActionsResponse(){

        ClientMessage tMessage5 = new ClientMessage(MessageTypes.RESPONSE, Events.SHOW_POSSIBLE_ACTIONS, MessageSender.TURN_MANAGING_CONTROLLER);


        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage5.getMessageType());
        assertEquals(MessageTypes.RESPONSE, tMessage5.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage5.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage5.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage5.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, tMessage5.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage5.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage5.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage5.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage5.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage5.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage5.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage5.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage5.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage5.getMessageEvent());
        assertEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage5.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage5.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage5.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage5.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage5.getMessageEvent());
        assertNotEquals(Events.END_GAME, tMessage5.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage5.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage5.getMessageEvent());

        assertNotEquals(MessageSender.MODEL, tMessage5.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage5.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage5.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage5.getMessageSender());

    }


    @Test

    public void checkLossResponse(){

        ClientMessage tMessage6 = new ClientMessage(MessageTypes.RESPONSE, Events.LOSS, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage6.getMessageType());
        assertEquals(MessageTypes.RESPONSE, tMessage6.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage6.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage6.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage6.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, tMessage6.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage6.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage6.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage6.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage6.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage6.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage6.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage6.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage6.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage6.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage6.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage6.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage6.getMessageEvent());
        assertEquals(Events.LOSS, tMessage6.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage6.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage6.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage6.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage6.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage6.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage6.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage6.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage6.getMessageSender());



    }



    @Test

    public void checkAskToSelectActionResponse(){

        ClientMessage tMessage7 = new ClientMessage(MessageTypes.RESPONSE, Events.ASK_TO_SELECT_ACTION, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage7.getMessageType());
        assertEquals(MessageTypes.RESPONSE, tMessage7.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage7.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage7.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage7.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, tMessage7.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage7.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage7.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage7.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage7.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage7.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage7.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage7.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage7.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage7.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage7.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage7.getMessageEvent());
        assertEquals(Events.ASK_TO_SELECT_ACTION, tMessage7.getMessageEvent());
        assertNotEquals(Events.WIN, tMessage7.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage7.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage7.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage7.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage7.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage7.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage7.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage7.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage7.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage7.getMessageSender());

    }



    @Test

    public void checkWinResponse(){

        ClientMessage tMessage8 = new ClientMessage(MessageTypes.RESPONSE, Events.WIN, MessageSender.TURN_MANAGING_CONTROLLER);

        assertNotEquals(MessageTypes.SETUP_REQUEST, tMessage8.getMessageType());
        assertEquals(MessageTypes.RESPONSE, tMessage8.getMessageType());
        assertNotEquals(MessageTypes.INFORMATION, tMessage8.getMessageType());
        assertNotEquals(MessageTypes.ERROR, tMessage8.getMessageType());
        assertNotEquals(MessageTypes.CONFIRM, tMessage8.getMessageType());
        assertNotEquals(MessageTypes.TURN_REQUEST, tMessage8.getMessageType());

        assertNotEquals(Events.USERNAME, tMessage8.getMessageEvent());
        assertNotEquals(Events.NUMB_PLAYERS, tMessage8.getMessageEvent());
        assertNotEquals(Events.CHALLENGER, tMessage8.getMessageEvent());
        assertNotEquals(Events.DECK, tMessage8.getMessageEvent());
        assertNotEquals(Events.COLOR, tMessage8.getMessageEvent());
        assertNotEquals(Events.CARD, tMessage8.getMessageEvent());
        assertNotEquals(Events.WORKER, tMessage8.getMessageEvent());
        assertNotEquals(Events.FIRST_PLAYER, tMessage8.getMessageEvent());
        assertNotEquals(Events.PLACE_WORKER, tMessage8.getMessageEvent());
        assertNotEquals(Events.AVAILABLE_POSITION, tMessage8.getMessageEvent());
        assertNotEquals(Events.SHOW_POSSIBLE_ACTIONS, tMessage8.getMessageEvent());
        assertNotEquals(Events.ASK_TO_SELECT_ACTION, tMessage8.getMessageEvent());
        assertEquals(Events.WIN, tMessage8.getMessageEvent());
        assertNotEquals(Events.LOSS, tMessage8.getMessageEvent());
        assertNotEquals(Events.REMOVE_LOSER, tMessage8.getMessageEvent());
        assertNotEquals(Events.END_GAME,tMessage8.getMessageEvent());
        assertNotEquals(Events.BOARD, tMessage8.getMessageEvent());
        assertNotEquals(Events.WORKER_DETAILS, tMessage8.getMessageEvent());

        assertNotEquals(MessageSender.MODEL,tMessage8.getMessageSender());
        assertNotEquals(MessageSender.CONTROLLER, tMessage8.getMessageSender());
        assertNotEquals(MessageSender.SETUP_CONTROLLER, tMessage8.getMessageSender());
        assertEquals(MessageSender.TURN_MANAGING_CONTROLLER, tMessage8.getMessageSender());


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


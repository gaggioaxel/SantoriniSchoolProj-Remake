package main.java.client.view;


import main.java.client.view.ClientInfo.GameInfo;
import main.java.shared.model.*;
import main.java.shared.observer.*;
import main.java.shared.message.ClientMessage;
import main.java.shared.message.Message;
import main.java.shared.socket.Client;
import main.java.shared.utils.CircularList;
import main.java.shared.utils.Point;

import java.util.ArrayList;


public abstract class View extends Observable<ClientMessage> implements Observer<Message> {

    protected String username;

    protected GameInfo gameInfo;

    protected String color;

    protected String userCard;

    protected PlayerDetails playerInfo;

    protected int numberOfPlayers;

    protected String challenger;

    protected String firstPlayer;

    protected ArrayList<CardDetails> handConfirmed;

    protected ArrayList<String> hand;

    protected CircularList<String> playersUsername;

    protected CircularList<PlayerDetails> playersInGame;

    protected ArrayList<String> colorList;

    protected ClientMessage response;

    protected ArrayList<Point> availablePositions;

    protected ArrayList<Action> possibleActions;

    protected String userNameMessage;

    protected WorkerDetails workerMessage;

    protected BoardDetails board;

    public abstract void setClient(Client client);
}



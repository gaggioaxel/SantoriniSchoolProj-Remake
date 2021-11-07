package main.java.server.model.turn.strategy;

import main.java.server.model.action.ActionsCreator;
import main.java.shared.utils.Tuple;

import java.util.ArrayList;

/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */

public interface Turn {

    Tuple<TurnPhase, ActionsCreator> getNextPhase();

    ArrayList<Tuple<TurnPhase, ActionsCreator>> getAllPhases();

    Tuple<TurnPhase, ActionsCreator> pollNextPhase();

    boolean isTurnStarting();

    void undoPhase();

    void reloadTurnPhases();

}
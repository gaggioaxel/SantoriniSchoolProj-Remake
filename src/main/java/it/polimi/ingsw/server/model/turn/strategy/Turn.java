package it.polimi.ingsw.server.model.turn.strategy;

import it.polimi.ingsw.server.model.action.ActionsCreator;
import it.polimi.ingsw.shared.utils.Tuple;

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
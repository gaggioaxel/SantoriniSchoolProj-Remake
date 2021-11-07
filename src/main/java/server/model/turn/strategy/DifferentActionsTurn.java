package main.java.server.model.turn.strategy;

import main.java.server.model.action.ActionsCreator;
import main.java.shared.utils.Tuple;

import java.util.LinkedList;

public class DifferentActionsTurn extends StandardActionsTurn {

    /**
     * creates a turn with different turn phases than the standard ones (like build before move)
     * @param phases to pass
     */
    public DifferentActionsTurn(LinkedList<Tuple<TurnPhase, ActionsCreator>> phases) {
        super();
        super.remainingPhases.addAll(phases);
    }

}

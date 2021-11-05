package it.polimi.ingsw.server.model.turn.strategy;

import it.polimi.ingsw.server.model.action.ActionsCreator;
import it.polimi.ingsw.shared.utils.Tuple;

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

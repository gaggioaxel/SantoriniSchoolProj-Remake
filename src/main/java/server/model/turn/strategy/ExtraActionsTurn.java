package main.java.server.model.turn.strategy;


import main.java.server.model.action.ActionsCreator;
import main.java.shared.utils.Tuple;

import java.util.LinkedList;

public class ExtraActionsTurn extends StandardActionsTurn {


    /**
     * creates a turn with more turn phases than the standard ones (with optionals)
     * @param phases to pass
     */
    public ExtraActionsTurn(LinkedList<Tuple<TurnPhase, ActionsCreator>> phases) {
        super();
        super.remainingPhases.addAll(phases);
    }

}

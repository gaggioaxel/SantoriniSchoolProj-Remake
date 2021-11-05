package it.polimi.ingsw.server.model.turn.strategy;


import it.polimi.ingsw.server.model.action.ActionsCreator;
import it.polimi.ingsw.shared.utils.Tuple;


public class TurnStrategy {

    private final Turn turn;

    /**
     * encapsulate a turn in a strategy
     * @param turnStrategy to set
     */
    public TurnStrategy(Turn turnStrategy) {
        turn = turnStrategy;
    }

    /**
     * gets the turn
     * @return the turn
     */
    public Turn getTurnStrategy() {
        return turn;
    }

    /**
     * moves the turn forward of one phase
     * @return the next phase in the turn
     */
    public Tuple<TurnPhase, ActionsCreator> getNextPhase() {
        return turn.getNextPhase();
    }

    /**
     * reloads all the turn phases
     */
    public void reloadTurnPhases() {
        turn.reloadTurnPhases();
    }

}

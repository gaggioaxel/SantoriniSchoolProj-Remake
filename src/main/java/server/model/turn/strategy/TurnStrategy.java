package main.java.server.model.turn.strategy;


import main.java.server.model.action.ActionsCreator;
import main.java.shared.utils.Tuple;


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

package main.java.server.model.turn.win;

import main.java.server.model.Board;
import main.java.shared.model.Action;

public class TwoOrMoreLevelsJumpDownCondition extends StandardWinCondition {

    /**
     * checks if action passed is a move with delta levels of 2 or more
     * @param moveAction to check
     * @param board that checks
     * @return true if meet the condition
     */
    @Override
    public boolean meetWinCondition(Action moveAction, Board board) {
        return moveAction.getActionType().equals(Action.ActionType.MOVE) && board.getDeltaLevel(moveAction.getFrom(), moveAction.getTo()) > 1;
    }

}

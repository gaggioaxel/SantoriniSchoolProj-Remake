package it.polimi.ingsw.server.model.turn.win;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.shared.model.Action;

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

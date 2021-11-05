package it.polimi.ingsw.server.model.turn.win;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.model.BuildLevel;

public class StandardWinCondition implements WinCondition {


    /**
     * checks if action passed is a move && it to a level 3 pos
     * @param moveAction to check
     * @param board that checks
     * @return true if is a move and and to a level 3
     */
    @Override
    public boolean meetWinCondition(Action moveAction, Board board) {
        return moveAction.getActionType().equals(Action.ActionType.MOVE) &&
                board.getTile(moveAction.getTo()).getTopLevel().equals(BuildLevel.LEVEL_3);
    }

}

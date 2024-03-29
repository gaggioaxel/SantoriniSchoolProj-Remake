package main.java.server.model.turn.win.limiter;

import main.java.server.model.Board;
import main.java.shared.model.Action;

public class PerimeterWinLimiter implements WinLimiter {

    /**
     * checks if the action made meets the condition of not being a move over a perimeter pos
     * @param moveMade last move made
     * @param board to check if is a perimeter
     * @return true if moveMade not null and is a move on the perimeter
     */
    @Override
    public boolean meetWinLimiter(Action moveMade, Board board) {
        return moveMade!=null && board.isPerimeter(moveMade.getTo());
    }

}

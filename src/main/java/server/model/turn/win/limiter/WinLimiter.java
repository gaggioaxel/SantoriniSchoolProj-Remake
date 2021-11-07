package main.java.server.model.turn.win.limiter;

import main.java.server.model.Board;
import main.java.shared.model.Action;

public interface WinLimiter {

    boolean meetWinLimiter(Action moveMade, Board board);

}

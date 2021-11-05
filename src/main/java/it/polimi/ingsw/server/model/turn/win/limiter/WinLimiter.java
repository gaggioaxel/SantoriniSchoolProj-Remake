package it.polimi.ingsw.server.model.turn.win.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.shared.model.Action;

public interface WinLimiter {

    boolean meetWinLimiter(Action moveMade, Board board);

}

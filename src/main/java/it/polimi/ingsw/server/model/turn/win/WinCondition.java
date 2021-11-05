package it.polimi.ingsw.server.model.turn.win;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.shared.model.Action;

public interface WinCondition {

    boolean meetWinCondition(Action moveAction, Board board);

}

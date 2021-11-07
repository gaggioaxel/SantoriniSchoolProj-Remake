package main.java.server.model.turn.win;

import main.java.server.model.Board;
import main.java.shared.model.Action;

public interface WinCondition {

    boolean meetWinCondition(Action moveAction, Board board);

}

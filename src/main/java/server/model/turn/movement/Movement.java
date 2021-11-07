package main.java.server.model.turn.movement;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.movement.limiter.MovementLimiter;
import main.java.shared.model.Action;

import java.util.ArrayList;

public interface Movement {

    ArrayList<Action> getMoves(Player player, Board board, ActionsCache cache, ArrayList<MovementLimiter> limiters);

}

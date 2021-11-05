package it.polimi.ingsw.server.model.turn.movement;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.movement.limiter.MovementLimiter;
import it.polimi.ingsw.shared.model.Action;

import java.util.ArrayList;

public interface Movement {

    ArrayList<Action> getMoves(Player player, Board board, ActionsCache cache, ArrayList<MovementLimiter> limiters);

}

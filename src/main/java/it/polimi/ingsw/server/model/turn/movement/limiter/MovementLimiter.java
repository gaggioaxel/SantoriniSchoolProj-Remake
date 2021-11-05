package it.polimi.ingsw.server.model.turn.movement.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

public interface MovementLimiter {

    ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache);

}

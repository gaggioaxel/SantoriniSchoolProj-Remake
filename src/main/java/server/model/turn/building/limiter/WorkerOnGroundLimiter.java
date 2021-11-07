package main.java.server.model.turn.building.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.BuildLevel;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class WorkerOnGroundLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        return board.getTile(from).getTopLevel().equals(BuildLevel.GROUND) ? tos : new ArrayList<>(1);
    }

}

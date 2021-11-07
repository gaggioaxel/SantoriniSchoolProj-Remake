package main.java.server.model.turn.building.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;

//Demeter
public class DifferentPlaceBuildLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        ArrayList<Point> copy = new ArrayList<>(tos.size());
        for (Point p : tos)
            if(!p.equals(cache.getPlayerAction(player, Action.ActionType.BUILD).getTo()))
                copy.add(p);
        return copy;
    }

}

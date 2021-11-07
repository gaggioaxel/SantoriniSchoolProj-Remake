package main.java.server.model.turn.building.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;

//Hephaestus pt.1
public class SamePlaceBuildLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        ArrayList<Point> samePlace = new ArrayList<>(1);
        for(Point to : tos)
            if (to.equals(cache.getPlayerAction(player, Action.ActionType.BUILD).getTo()))
                samePlace.add(to);
        return samePlace;
    }
}

package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.utils.Point;

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

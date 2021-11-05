package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.utils.Point;

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

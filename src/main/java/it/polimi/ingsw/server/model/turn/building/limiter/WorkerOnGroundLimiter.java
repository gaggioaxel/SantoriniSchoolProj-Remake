package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

public class WorkerOnGroundLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        return board.getTile(from).getTopLevel().equals(BuildLevel.GROUND) ? tos : new ArrayList<>(1);
    }

}

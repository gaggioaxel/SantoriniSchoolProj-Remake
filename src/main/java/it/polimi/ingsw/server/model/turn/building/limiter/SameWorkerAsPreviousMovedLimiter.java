package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

public class SameWorkerAsPreviousMovedLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        return board.getTile(from).getWorker().equals((Worker) cache.getPlayerAction(player, Action.ActionType.MOVE).getWorker()) ? tos : new ArrayList<>(1);
    }
}

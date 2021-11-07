package main.java.server.model.turn.building.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.Worker;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class DifferentWorkerThanMovedPreviousLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        return board.getTile(from).getWorker().equals((Worker) cache.getPlayerAction(player, Action.ActionType.MOVE).getWorker()) ? new ArrayList<>(1) : tos;
    }

}

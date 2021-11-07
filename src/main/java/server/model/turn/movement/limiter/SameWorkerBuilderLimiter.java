package main.java.server.model.turn.movement.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.Worker;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class SameWorkerBuilderLimiter implements MovementLimiter {


    /**
     * checks if the worker in from pos is the same that built before
     * @param player acting
     * @param from pos of the worker
     * @param tos to limiter
     * @param board that checks poses
     * @param cache that gets latest build action
     * @return tos if it's the same worker, an empty list otherwise
     */
    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        return (board.getTile(from).getWorker().equals((Worker)cache.getPlayerAction(player, Action.ActionType.BUILD).getWorker())) ? tos : new ArrayList<>(1);
    }

}

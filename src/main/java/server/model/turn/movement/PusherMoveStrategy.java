package main.java.server.model.turn.movement;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PusherMoveStrategy extends StandardMoveStrategy {


    /**
     * applies the un-pushable filter
     * @param player passed down
     * @param board passed down
     * @param from passed down
     * @param tos passed down
     * @return tos filtered
     */
    @Override
    protected ArrayList<Point> applyFilters(Player player, Board board, Point from, ArrayList<Point> tos) {
        return applyUnPushableWorkersFilter(board, player, from, board.applyMoreOneStepUpFilter(from, board.applyDomeFilter(tos)));
    }


    /**
     * if there's a worker in a to tile it creates a forced move for it
     * @param player unused
     * @param board unused
     * @param from pos starting
     * @param tos to build a forced move
     * @return the map of tos with push or null
     */
    @Override
    protected Map<Point, Action> getForcedMoves(Player player, Board board, Point from, ArrayList<Point> tos) {
        Map<Point, Action> forcedMoves = new HashMap<>();
        tos.forEach(to -> {
            if(board.getTile(to).hasWorker())
                forcedMoves.put(to, new Action(board.getTile(to).getWorker().getReadableCopy(), Action.ActionType.MOVE, to,
                        board.getNextLinedUpPos(from, to), null, null));
            else
                forcedMoves.put(to, null);
        });
        return forcedMoves;
    }


    /**
     * apply a pushable filter on to if there's a worker and is a player worker, or behind of that worker there's no board tile, nor a dome/worker
     * @param board to check points
     * @param player whose workers must be checked
     * @param from pos of worker pusher
     * @param tos poses to check
     * @return all tos filtered
     */
    private ArrayList<Point> applyUnPushableWorkersFilter(Board board, Player player, Point from, ArrayList<Point> tos) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> board.getTile(to).hasWorker() &&
                (player.isHisWorker(board.getTile(to).getWorker()) ||
                board.getNextLinedUpPos(from, to) == null ||
                board.getTile(board.getNextLinedUpPos(from, to)).hasWorker() ||
                board.getTile(board.getNextLinedUpPos(from, to)).hasDome()));
        return copy;
    }

}

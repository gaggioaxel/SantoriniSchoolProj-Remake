package main.java.server.model.turn.movement;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.movement.limiter.MovementLimiter;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class ExtraMoveStrategy extends StandardMoveStrategy {


    /**
     * gets around poses and around poses of around poses
     * @param player that acts
     * @param board passed down
     * @param from pos of worker
     * @param cache passed down
     * @param limiters applied to limiter poses
     * @return all poses
     */
    @Override
    protected ArrayList<Point> getPosesPerWorker(Player player, Board board, Point from, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        ArrayList<Point> tos = applyFilters(player, board, from, board.getNearPoses(from, 1));
        for (MovementLimiter limiter : limiters)
            tos = limiter.applyLimiter(player, from, tos, board, cache);

        ArrayList<Point> tosOfTos = new ArrayList<>(20);
        for(Point to : tos) {
            ArrayList<Point> nearby = applyFilters(player, board, from, board.getNearPoses(to, 1));
            for (MovementLimiter limiter : limiters)
                nearby = limiter.applyLimiter(player, from, nearby, board, cache);
            tosOfTos.addAll(nearby);
        }
        return removeDuplicates(tosOfTos);
    }

}

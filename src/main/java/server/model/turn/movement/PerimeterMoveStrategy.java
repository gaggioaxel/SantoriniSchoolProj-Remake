package main.java.server.model.turn.movement;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.movement.limiter.MovementLimiter;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class PerimeterMoveStrategy extends StandardMoveStrategy {


    /**
     * creates a pool of poses linked in perimeter, removes duplicates, then checks if it can move back to initial position
     * @param player that acts
     * @param board that applies the algorithm
     * @param from pos starting
     * @param cache passed down for limiters
     * @param limiters passed down
     * @return all the linked poses
     */
    @Override
    protected ArrayList<Point> getPosesPerWorker(Player player, Board board, Point from, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        ArrayList<Point> tos = applyFilters(player, board, from, board.getNearPoses(from, 1));
        ArrayList<Point> poolPerimeterPointsList = new ArrayList<>(tos.size()+144);
        for (Point to : tos)
            if(board.isPerimeter(to))
                poolPerimeterPointsList.addAll(board.getLinkedPerimeterAndNear(player, to, cache, limiters));
        poolPerimeterPointsList.addAll(tos);
        poolPerimeterPointsList = removeDuplicates(poolPerimeterPointsList);
        outerLoop :
        for(Point p : poolPerimeterPointsList)
            for(Point near : board.getNearPoses(from, 1))
                if(board.isPerimeter(p) && p.equals(near) && board.getDeltaLevel(from, near) < 2) {
                    ArrayList<Point> back = new ArrayList<>(1);
                    back.add(from);
                    for(MovementLimiter limiter : limiters)
                        back = limiter.applyLimiter(player, near, back, board, cache);
                    poolPerimeterPointsList.addAll(back);
                    break outerLoop;
                }
        return poolPerimeterPointsList;
    }



}

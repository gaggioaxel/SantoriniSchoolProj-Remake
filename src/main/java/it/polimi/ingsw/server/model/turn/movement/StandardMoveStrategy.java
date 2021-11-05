package it.polimi.ingsw.server.model.turn.movement;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.movement.limiter.MovementLimiter;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.shared.model.Action.ActionType;

public class StandardMoveStrategy implements Movement {


    /**
     * create moves by getting poses and forced moves for every worker
     * @param player acting
     * @param board to get poses around
     * @param cache passed down for checking limiters
     * @param limiters to be applied
     * @return all the moves action available
     */
    @Override
    public ArrayList<Action> getMoves(Player player, Board board, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        ArrayList<Action> actions = new ArrayList<>(20);

        for(Worker w : player.getWorkers()) {
            actions.addAll(makeMoveActions(w, board.getWorkerPos(w),
                    getForcedMoves(player, board, board.getWorkerPos(w),
                            getPosesPerWorker(player, board, board.getWorkerPos(w), cache, limiters))));
        }

        return actions;
    }


    /**
     * applies filters from near poses
     * @param player passed down
     * @param board to get poses around
     * @param from pos of worker
     * @param cache to apply limiters
     * @param limiters to apply
     * @return list of all points the available point of the worker
     */
    protected ArrayList<Point> getPosesPerWorker(Player player, Board board, Point from, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        ArrayList<Point> tos = applyFilters(player, board, from, board.getNearPoses(from, 1));
        for (MovementLimiter limiter : limiters)
            tos = limiter.applyLimiter(player, from, tos, board, cache);
        return tos;
    }


    /**
     * calls step up limiter
     * @param player passed down
     * @param board passed down
     * @param from passed down
     * @param tos passed down
     * @return poses limited by a step up filter
     */
    protected ArrayList<Point> applyFilters(Player player, Board board, Point from, ArrayList<Point> tos) {
        return board.applyMoreOneStepUpFilter(from, board.applyWorkersFilter(board.applyDomeFilter(tos)));
    }


    /**
     * puts to null every forced move because in standard instance are all null. used for inheritance
     * @param player unused
     * @param board unused
     * @param from unused
     * @param tos to build a null forced move
     * @return a map of tos to null
     */
    protected Map<Point, Action> getForcedMoves(Player player, Board board, Point from, ArrayList<Point> tos) {
        Map<Point, Action> forcedMoves = new HashMap<>();
        tos.forEach(to -> forcedMoves.put(to, null));
        return forcedMoves;
    }


    /**
     * creates the move actions from tos and forced map
     * @param worker that acts
     * @param from position
     * @param tosAndForcedActionsMap map that has every to position and if there's a forced move
     * @return list of actions
     */
    protected ArrayList<Action> makeMoveActions(Worker worker, Point from, Map<Point, Action> tosAndForcedActionsMap) {
        ArrayList<Action> actions = new ArrayList<>(8);
        tosAndForcedActionsMap.keySet().forEach(to -> actions.add(new Action(worker.getReadableCopy(), ActionType.MOVE, from, to, tosAndForcedActionsMap.get(to), null)));
        return actions;
    }


    /**
     * for every point of arr puts it in a copy if not found, then returns the copy cleaned of doubles
     * @param arr to clear
     * @return cleaned poses
     */
    protected ArrayList<Point> removeDuplicates(ArrayList<Point> arr) {
        ArrayList<Point> clean = new ArrayList<>(24);
        boolean found;
        for(Point e : arr) {
            found = false;
            for (Point el : clean)
                if (el.equals(e)) {
                    found = true;
                    break;
                }
            if(!found)
                clean.add(e);
        }
        return clean;
    }

}

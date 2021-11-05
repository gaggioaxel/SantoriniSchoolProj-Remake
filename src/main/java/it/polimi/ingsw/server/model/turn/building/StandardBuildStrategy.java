package it.polimi.ingsw.server.model.turn.building;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.building.limiter.BuildingLimiter;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

public class StandardBuildStrategy implements Building {


    /**
     * creates standard build actions by cycling over both workers' around poses and by applying filters to those poses and extensions inherited
     * @param player that acts
     * @param board that checks points
     * @param cache that checks previous actions
     * @param limiters that limits round poses
     * @return list of actions available for the player that acts
     */
    @Override
    public ArrayList<Action> getBuildActions(Player player, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters) {
        ArrayList<Action> builds = new ArrayList<>(12);

        for (Worker worker : player.getWorkers()) {
            Point from = board.getWorkerPos(worker);
            ArrayList<Point> nearValidPoses = getFilteredPoses(player, worker, board, cache, limiters);
            nearValidPoses.forEach(validTo -> builds.add(new Action(worker.getReadableCopy(), Action.ActionType.BUILD, from, validTo, null,
                    BuildLevel.values()[board.getTile(validTo).getIntLevel()+1])));
        }

        return builds;
    }


    /**
     * applies limiters
     * @param player passed
     * @param worker passed
     * @param board passed
     * @param cache passed
     * @param limiters passed
     * @return list of points filtered
     */
    protected ArrayList<Point> getFilteredPoses(Player player, Worker worker, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters) {
        return applyLimiters(player, worker, board, cache, limiters, board.getNearPoses(board.getWorkerPos(worker), 1));
    }


    /**
     * checks if round poses have workers or domes, or is limited by limiters and then removes those poses
     * @param player that acts
     * @param worker that build
     * @param board to check poses
     * @param cache to check previous actions
     * @param limiters to be applied
     * @param tos points to check
     * @return tos limited by the limiters
     */
    protected ArrayList<Point> applyLimiters(Player player, Worker worker, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters, ArrayList<Point> tos) {
        tos.removeIf(to -> board.getTile(to).hasWorker() || board.getTile(to).hasDome());
        if(limiters!=null)
            for (BuildingLimiter limiter : limiters)
                tos = limiter.applyLimiter(player, board.getWorkerPos(worker), tos, board, cache);
        return tos;
    }

}

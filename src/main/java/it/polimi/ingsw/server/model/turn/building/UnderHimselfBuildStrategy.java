package it.polimi.ingsw.server.model.turn.building;


import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.building.limiter.BuildingLimiter;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

public class UnderHimselfBuildStrategy extends StandardBuildStrategy {


    /**
     * creates available poses for next action and insert under worker pos if is not limited by limiters or a level 3
     * @param player that acts
     * @param worker that builds
     * @param board to check poses
     * @param cache to check previous actions
     * @param limiters to limit poses
     * @return poses around for actions and pos under himself
     */
    @Override
    protected ArrayList<Point> getFilteredPoses(Player player, Worker worker, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters) {
        ArrayList<Point> nearPoses = super.getFilteredPoses(player, worker, board, cache, limiters);
        ArrayList<Point> fromAsList = new ArrayList<>(1);
        fromAsList.add(board.getWorkerPos(worker));
        fromAsList.removeIf(from -> board.getTile(from).getTopLevel().equals(BuildLevel.LEVEL_3));
        if(limiters!=null)
            for (BuildingLimiter limiter : limiters)
                fromAsList = limiter.applyLimiter(player, board.getWorkerPos(worker), fromAsList, board, cache);
        nearPoses.addAll(fromAsList);
        return nearPoses;
    }
}

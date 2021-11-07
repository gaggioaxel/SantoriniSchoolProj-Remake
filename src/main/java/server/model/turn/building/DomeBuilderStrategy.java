package main.java.server.model.turn.building;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.building.limiter.BuildingLimiter;
import main.java.shared.model.Action;
import main.java.shared.model.BuildLevel;
import main.java.shared.model.WorkerDetails;
import main.java.shared.utils.Point;

import java.util.ArrayList;

import static main.java.shared.model.Action.ActionType;

public class DomeBuilderStrategy extends StandardBuildStrategy {

    /**
     * create the standard build actions with dome variation for every point that has a level 2 or lower
     * @param player that acts
     * @param board passed up
     * @param cache passed up
     * @param limiters passed up
     * @return standard and dome variant of build actions
     */
    @Override
    public ArrayList<Action> getBuildActions(Player player, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters) {
        ArrayList<Action> builds = super.getBuildActions(player, board, cache, limiters);
        ArrayList<Action> copy = new ArrayList<>(builds);
        builds.forEach(act -> {
            if(!act.getBuildLevel().equals(BuildLevel.DOME))
                copy.add(getDomeBuildingVariant(act.getWorker(), act.getFrom(), act.getTo()));
        });

        return copy;
    }

    /**
     * creates a dome building action for the worker in that point
     * @param worker that builds
     * @param from pos of worker
     * @param to pos of building
     * @return the dome variant
     */
    private Action getDomeBuildingVariant(WorkerDetails worker, Point from, Point to) {
        return new Action(worker, ActionType.BUILD, from, to, null, BuildLevel.DOME);
    }

}

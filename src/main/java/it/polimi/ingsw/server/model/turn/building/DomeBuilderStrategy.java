package it.polimi.ingsw.server.model.turn.building;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.building.limiter.BuildingLimiter;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.model.WorkerDetails;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

import static it.polimi.ingsw.shared.model.Action.ActionType;

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

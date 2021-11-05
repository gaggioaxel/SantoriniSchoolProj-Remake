package it.polimi.ingsw.server.model.action;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.turn.PassiveEffect;
import it.polimi.ingsw.server.model.turn.building.Building;
import it.polimi.ingsw.server.model.turn.building.limiter.BuildingLimiter;
import it.polimi.ingsw.server.model.turn.movement.Movement;
import it.polimi.ingsw.server.model.turn.movement.limiter.MovementLimiter;
import it.polimi.ingsw.shared.model.Action;

import java.util.ArrayList;

public class ActionsCreator {

    @Expose
    @SerializedName("move_type")
    private final Movement movement;

    @Expose
    @SerializedName("move_limiters")
    private final ArrayList<MovementLimiter> movementLimiters;

    @Expose
    @SerializedName("building_type")
    private final Building building;

    @Expose
    @SerializedName("build_limiters")
    private final ArrayList<BuildingLimiter> buildingLimiters;


    public ActionsCreator(Movement m, ArrayList<MovementLimiter> mls, Building b, ArrayList<BuildingLimiter> bls) {
        movement = m;
        movementLimiters = mls;
        building = b;
        buildingLimiters = bls;
    }

    /**
     * create the action based on the movement and/or build strategy
     * checks if it's movement or a building then adds all the limiters (internal and external)
     * @param player that needs the actions to be created
     * @param board param for actions creation
     * @param cache param for actions creation
     * @param extMLs param for actions creation
     * @param extBLs currently unused
     * @return available actions (always not null)
     */
    public ArrayList<Action> getActions(Player player, Board board, ActionsCache cache, ArrayList<MovementLimiter> extMLs, ArrayList<BuildingLimiter> extBLs) {
        if (isMovement()) {
            ArrayList<MovementLimiter> totalLimiters = new ArrayList<>(4);
            if(movementLimiters!=null)
                totalLimiters.addAll(movementLimiters);
            if (extMLs != null)
                totalLimiters.addAll(extMLs);
            return movement.getMoves(player, board, cache, totalLimiters);
        } else {
            ArrayList<BuildingLimiter> totalLimiters = new ArrayList<>(4);
            if(buildingLimiters!=null)
                totalLimiters.addAll(buildingLimiters);
            if (extBLs != null) //extBLs are not present in this implementation so i never enter this if
                totalLimiters.addAll(extBLs);
            return building.getBuildActions(player, board, cache, totalLimiters);
        }
    }

    /**
     * checks if it has movement element
     * @return true if there's a movement instance
     */
    private boolean isMovement() {
        return movement!=null;
    }

    /**
     * creates a skippable action (action with pass type)
     * @return the action of passing
     */
    public static Action getSkippableAction() {
        return new Action(null, Action.ActionType.PASS, null, null, null, null);
    }

    /**
     * called once set cards to get passive effects to map on player (so i know they must be applied on enemies until
     * when the player is not removed
     * @return every element of the whole effect that have a passiveEffect implementation.
     */
    public PassiveEffect removePassiveEffect() {
        if(movementLimiters!=null)
            for (MovementLimiter limiter : movementLimiters) {
                if(limiter instanceof PassiveEffect && ((PassiveEffect) limiter).isInAPassiveInstance())
                    return (PassiveEffect) movementLimiters.remove(movementLimiters.indexOf(limiter));
            }
        return null;
    }

}

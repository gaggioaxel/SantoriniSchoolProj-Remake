package main.java.server.model.turn.movement.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.PassiveEffect;
import main.java.shared.model.Action;
import main.java.shared.utils.Point;

import java.util.ArrayList;

public class CantMoveUpLimiter implements MovementLimiter, PassiveEffect {

    /**
     * to differentiate between athena passive effect and limiter
     */
    public enum PassiveEffectTarget {
        SELF,
        ENEMIES
    }
    private final PassiveEffectTarget target;

    /**
     * checks if the target of this instance are the enemies
     * @return true if target are the enemies
     */
    @Override
    public boolean isInAPassiveInstance() {
        return target == PassiveEffectTarget.ENEMIES;
    }


    /**
     * checks if it's applicable on enemies by checking if targets are enemies and latest move was to an upper level
     * @param caster player that applies the effect
     * @param lastAction latest action
     * @param board used to check delta levels
     * @return true if target is enemies and level variation is > 0
     */
    @Override
    public boolean getApplicabilityOnEnemies(Player caster, Action lastAction, Board board) {
        return !target.equals(PassiveEffectTarget.SELF) &&
                (lastAction!=null && board.getDeltaLevel(lastAction.getTo(), lastAction.getFrom()) > 0);
    }


    /**
     * creates the instance
     * @param target to set
     */
    public CantMoveUpLimiter(PassiveEffectTarget target) {
        this.target = target;
    }


    /**
     * applies limiter (for the passive instance)
     * @param player unused
     * @param from point
     * @param tos to check
     * @param board that checks
     * @param cache unused
     * @return copy of tos only on a same level
     */
    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> board.getDeltaLevel(to, from) > 0);
        return copy;
    }
}

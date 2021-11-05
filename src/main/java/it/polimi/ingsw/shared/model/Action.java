package it.polimi.ingsw.shared.model;

import it.polimi.ingsw.shared.utils.Point;

import java.io.Serializable;


public class Action implements Serializable {
    private final WorkerDetails worker;
    public enum ActionType {
        MOVE,
        BUILD,
        PASS
    }
    private final ActionType actionType;
    private final Point from;
    private final Point to;
    private final Action forcedMove;
    private final BuildLevel buildLevel;

    /**
     * creates an action based on
     * @param worker that made the action (null if action type is a pass)
     * @param actionType if is a move
     * @param from position where the worker started acting
     * @param to position where the worker ended acting
     * @param forcedMove not null if has forced an action on another worker (by pushing him or swapping with him)
     * @param buildLevel the level a worker can build
     */
    public Action(WorkerDetails worker, ActionType actionType, Point from, Point to, Action forcedMove, BuildLevel buildLevel) {
        this.worker = worker;
        this.actionType = actionType;
        this.from = from;
        this.to = to;
        this.forcedMove = forcedMove;
        this.buildLevel = buildLevel;
    }

    /**
     * getter
     * @return worker acting
     */
    public WorkerDetails getWorker() {
        return worker;
    }

    /**
     * getter
     * @return action type
     */
    public ActionType getActionType() {
        return this.actionType;
    }

    /**
     * getter
     * @return from position
     */
    public Point getFrom() {
        return this.from;
    }

    /**
     * getter
     * @return to position
     */
    public Point getTo() {
        return this.to;
    }

    /**
     * checks if has forced move
     * @return true if forced move is set
     */
    public boolean hasForcedMove() {
        return forcedMove != null;
    }

    /**
     * getter
     * @return the forced move
     */
    public Action getForcedMove() {
        return forcedMove;
    }

    /**
     * getter
     * @return the build level to set
     */
    public BuildLevel getBuildLevel() {
        return this.buildLevel;
    }


    /**
     * checks every member
     * @param a action to compare
     * @return true if every field matches
     */
    public boolean equals(Action a) {
        return (a!=null && actionType.equals(a.actionType))
                &&
                (worker!=null ? a.worker!=null && worker.equals(a.worker) : a.worker==null)
                &&
                (from!=null ? a.from!=null && from.equals(a.from) : a.from==null)
                &&
                (to!=null ? a.to!=null && to.equals(a.to) : a.to==null)
                &&
                (forcedMove!=null ? a.forcedMove!=null && forcedMove.equals(a.forcedMove) : a.forcedMove==null)
                &&
                (buildLevel!=null ? a.buildLevel!=null && buildLevel.equals(a.buildLevel) : a.buildLevel==null);
    }
}

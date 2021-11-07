package main.java.server.model.turn.strategy;

import com.google.gson.annotations.Expose;

public class TurnPhase {

    public enum TurnPhaseType {
        MOVE,
        BUILD,
    }
    @Expose
    private final TurnPhaseType type;
    @Expose
    private final boolean optional;


    /**
     * creates a new instance of a turn phase
     * @param type of action between move an build
     * @param optional if this instance of turn phase is optional
     */
    public TurnPhase(TurnPhaseType type, boolean optional) {
        this.type = type;
        this.optional = optional;
    }


    /**
     * @return the type of action
     */
    public TurnPhaseType getType() {
        return type;
    }


    /**
     * checks if this phase is optional
     * @return true if it's optional
     */
    public boolean isOptional() {
        return optional;
    }


    /**
     * compares if the phase is not null then if the type and the optional fields are the same
     * @param tp to check
     * @return true f everything matches, false otherwise
     */
    public boolean equals(TurnPhase tp) {
        return tp!=null && tp.type.equals(type) && tp.optional==optional;
    }
}

package main.java.server.model.turn.strategy;

import com.google.gson.annotations.SerializedName;
import main.java.server.model.action.ActionsCreator;
import main.java.shared.utils.Tuple;

import java.util.ArrayList;
import java.util.LinkedList;

public class StandardActionsTurn implements Turn {

    @SerializedName("turn_phases_list")
    protected final LinkedList<Tuple<TurnPhase, ActionsCreator>> remainingPhases = new LinkedList<>();

    protected LinkedList<Tuple<TurnPhase, ActionsCreator>> storedPhases;


    /**
     * constructor for standard actions
     * @param movementStrategy is the strategy for movement actions
     * @param buildingStrategy is the strategy for building actions
     */
    public StandardActionsTurn(ActionsCreator movementStrategy, ActionsCreator buildingStrategy) {
        remainingPhases.add(new Tuple<>(new TurnPhase(TurnPhase.TurnPhaseType.MOVE, false), movementStrategy));
        remainingPhases.add(new Tuple<>(new TurnPhase(TurnPhase.TurnPhaseType.BUILD, false), buildingStrategy));
    }


    /**
     * constructor to be inherited by sub classes that need different phases rotation
     */
    protected StandardActionsTurn() { }


    /**
     * retrieves and stores the next phase in remaining phases
     * @return the top phase of the stack representation of the turn phases
     */
    @Override
    public Tuple<TurnPhase, ActionsCreator> pollNextPhase() {
        Tuple<TurnPhase, ActionsCreator> elem = remainingPhases.poll();
        if(elem!=null) {
            if (storedPhases == null)
                storedPhases = new LinkedList<>();
            storedPhases.push(elem);
        }
        return elem;
    }


    /**
     * retrieves but not removes the top phase of the remaining phases stack
     * @return the top phase
     */
    @Override
    public Tuple<TurnPhase, ActionsCreator> getNextPhase() {
        return remainingPhases.peek();
    }


    /**
     * checks if the polled phase is the first
     * @return true if stored phases contains one element
     */
    @Override
    public boolean isTurnStarting() {
        return storedPhases.size()==1;
    }


    /**
     * retrieves but does not change all remaining phases
     * @return a copy of all the remaining phases
     */
    @Override
    public ArrayList<Tuple<TurnPhase, ActionsCreator>> getAllPhases() {
        return new ArrayList<>(remainingPhases);
    }


    /**
     * removes and push the top phase of the remaining
     */
    @Override
    public void undoPhase() {
        remainingPhases.push(storedPhases.poll());
    }


    /**
     * polls and pushes all phases in order from stored to remaining
     */
    @Override
    public void reloadTurnPhases() {
        Tuple<TurnPhase, ActionsCreator> elem;
        while ((elem = storedPhases.poll())!=null)
            remainingPhases.push(elem);
    }

}

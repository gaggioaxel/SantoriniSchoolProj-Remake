package it.polimi.ingsw.shared.message;

import it.polimi.ingsw.shared.model.Action;

import java.util.ArrayList;


/**this message contains the arrayList of actions that the player can make*/

public class PossibleActionsModelMessage extends ModelMessage {

    private ArrayList<Action> possibleActionConfirmed;




    /**
     * PossibleActionsModelMessage's constructor
     * @param ty
     * @param to
     * @param send
     */

    public PossibleActionsModelMessage(MessageTypes ty, Events to, MessageSender send) {
        super(ty, to, send);
    }




    /**setter*/

    public void setPossibleActionConfirmed(ArrayList<Action> possibleActionConfirmed) {
        this.possibleActionConfirmed = possibleActionConfirmed;

    }



    /**getter*/

    public ArrayList<Action> getPossibleActionConfirmed() {
        return possibleActionConfirmed;
    }

}

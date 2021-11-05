package it.polimi.ingsw.shared.message;

import it.polimi.ingsw.shared.model.CardDetails;

import java.util.ArrayList;



/**this message is sent by the model*/


public class ModelMessage extends Message {

    private int numberOfPlayersConfirmed;

    private ArrayList<CardDetails> handConfirmed;





    /**constructor for the modelMessage
     *
     * @param ty represents the type of the message
     * @param to represents the event that is currently happening
     * @param send represents which entity sends the message
     */

    public ModelMessage(MessageTypes ty, Events to, MessageSender send) {

        super(ty, to, send);
    }





    /**setters for the model message*/


    public void setNumberOfPlayersConfirmed(int numberOfPlayersConfirmed) {
        this.numberOfPlayersConfirmed = numberOfPlayersConfirmed;
    }

    public void setHandConfirmed(ArrayList<CardDetails> handConfirmed) {
        this.handConfirmed = handConfirmed;
    }





    /**getters for the model message*/

    public int getNumberOfPlayersConfirmed() {
        return numberOfPlayersConfirmed;
    }

    public ArrayList<CardDetails> getHandConfirmed() {
        return handConfirmed;
    }

}

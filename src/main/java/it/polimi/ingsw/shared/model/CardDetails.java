package it.polimi.ingsw.shared.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */
public class CardDetails implements Serializable {

    @Expose
    @SerializedName("card_name")
    private final String cardName;
    @SerializedName("card_description")
    private String cardDescription;

    /**
     * creates a read only version of a card
     * @param cardName of the card
     * @param cardDescription of the card
     */
    public CardDetails(String cardName, String cardDescription) {
        this.cardName = cardName;
        this.cardDescription = cardDescription;
    }

    /**
     * getter
     * @return the card name
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * getter
     * @return the card description
     */
    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }
}
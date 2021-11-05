package it.polimi.ingsw.shared.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DeckDetails implements Cloneable, Serializable {

    protected final ArrayList<CardDetails> cardsDetails = new ArrayList<>();

    /**
     * creates a read only copy
     * @param cardsDetails to set
     */
    public DeckDetails(ArrayList<CardDetails> cardsDetails) {
        this.cardsDetails.addAll(cardsDetails);
    }

    /**
     * getter
     * @return all cards
     */
    public ArrayList<CardDetails> getAllCardsDetails() {
        return cardsDetails;
    }

    /**
     * creates a copy of this deck
     * @return this instance cloned
     * @throws CloneNotSupportedException never thrown
     */
    protected DeckDetails clone() throws CloneNotSupportedException {
        return (DeckDetails) super.clone();
    }
}


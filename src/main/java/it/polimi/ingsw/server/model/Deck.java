package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.utils.CardsJsonDeserializer;
import it.polimi.ingsw.shared.model.CardDetails;
import it.polimi.ingsw.shared.model.DeckDetails;

import java.util.ArrayList;


/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */

public class Deck extends DeckDetails {

    private final CardsJsonDeserializer cardsBuilder = new CardsJsonDeserializer();

    /**
     * creates the deck by setting all cards to deck details
     */
    public Deck() {
        super(new CardsJsonDeserializer().getDetailedCards());
    }


    /**
     * builds and get the card
     * @param cardName of card to build
     * @return full version of card (name with effects)
     */
    public Card getCard(String cardName) {
        return cardsBuilder.buildAndGetCard(cardName);
    }


    /**
     * reads all cards name, (descriptions will be read client side)
     * @return list of all cards name currently implemented
     */
    public ArrayList<String> getAllCardsName() {
        ArrayList<String> names = new ArrayList<>(super.cardsDetails.size());
        ArrayList<CardDetails> cds = super.cardsDetails;
        for(CardDetails cd : cds)
            names.add(cd.getCardName());
        return names;
    }


    /**
     * gets a copy read only of this deck to send to the client side
     * @return this instance of deck details
     * @throws CloneNotSupportedException never thrown
     */
    public DeckDetails getReadableCopy() throws CloneNotSupportedException {
        return super.clone();
    }

}
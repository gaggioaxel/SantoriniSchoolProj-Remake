package it.polimi.ingsw.server.model;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.turn.strategy.Turn;
import it.polimi.ingsw.server.model.turn.win.WinConditionsStorage;
import it.polimi.ingsw.shared.model.CardDetails;
import it.polimi.ingsw.shared.utils.Tuple;

public class Card extends CardDetails {

    private final Turn turn;

    @SerializedName("win")
    private final WinConditionsStorage winConditions;


    /**
     * creates a card instance with name and effect
     * @param name of the card
     * @param effect that applies
     */
    public Card(String name, Tuple<Turn, WinConditionsStorage> effect) {
        super(name, null);
        turn = effect!=null ? effect.x : null;
        winConditions = effect!=null ? effect.y : null;
    }


    /**
     * gets the turn
     * @return the turn correlated to this card
     */
    public Turn getTurn() {
        return turn;
    }


    /**
     * gets the win conditions correlated to this card
     * @return win condition
     */
    public WinConditionsStorage getWinChecker() {
        return winConditions;
    }


    /**
     * compares the card name to check if they are the same
     * @param c card to check
     * @return true if they have the same name, false if c is null or have different card name
     */
    public boolean equals(it.polimi.ingsw.server.model.Card c) {
        return c!=null && c.getCardName().equals(this.getCardName());
    }

}

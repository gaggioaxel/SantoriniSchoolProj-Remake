package main.java.server.model;

import com.google.gson.annotations.SerializedName;
import main.java.server.model.turn.strategy.Turn;
import main.java.server.model.turn.win.WinConditionsStorage;
import main.java.shared.model.CardDetails;
import main.java.shared.utils.Tuple;

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
    public boolean equals(Card c) {
        return c!=null && c.getCardName().equals(this.getCardName());
    }

}

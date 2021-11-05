package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.model.turn.strategy.Turn;
import it.polimi.ingsw.server.model.turn.strategy.StandardActionsTurn;
import it.polimi.ingsw.server.model.turn.win.WinConditionsStorage;
import it.polimi.ingsw.shared.utils.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Card card;
    Turn std;
    WinConditionsStorage ext;

    @BeforeEach
    void setup() {
        std = new StandardActionsTurn(null, null);
        ext = new WinConditionsStorage(null, null);
        card = new Card("franco", new Tuple<>(std, ext));
    }

    @Test
    void getTurn() {
        assertSame(std, card.getTurn());
    }

    @Test
    void getWinChecker() {
        assertSame(ext, card.getWinChecker());
    }
}
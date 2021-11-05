package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Card;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.shared.color.Color;
import it.polimi.ingsw.shared.model.PlayerDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private static final int NUMWORKERS = 2;
    private String username;
    private Card card;
    private Color color;
    private Worker[] workers;

    private Player p1;

    @BeforeEach
    void setUp() {
        p1 = new Player("Franco");
        p1.setCard(new Card("TrapCard", null));

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUsername() {
        assertSame("Franco", p1.getUsername());
        assertNotSame("Genoveffa", p1.getUsername());
        assertNotSame("fRanco", p1.getUsername());
    }

    @Test
    void getAndGetCard() {
        Card c = new Card("Franco", null);
        p1.setCard(c);
        assertSame(c, p1.getCard());
    }

    @Test
    void getAndSetColor() {
        Color c = new Color("pink");
        p1.setColor(c);
        assertSame(c, p1.getColor());
    }

    @Test
    void isHisWorker() {
        Worker w1 = new Worker();
        assertFalse(p1.isHisWorker(w1));
        assertTrue(p1.isHisWorker(p1.getWorkers()[0]));
        assertTrue(p1.isHisWorker(p1.getWorkers()[1]));
    }

    @Test
    void getReadableCopy() {
        PlayerDetails pl1 = p1.getReadableCopy();
        assertEquals(p1.getUsername(), pl1.getUsername());
        assertEquals(p1.getCard().getCardName(), pl1.getCard().getCardName());
        if(p1.getColor()==null)
            assertNull(pl1.getColor());
        else
            assertEquals(p1.getColor().getColorName(), pl1.getColor().getColorName());
        //assertEquals(p1.getWorkers().length, pl1.);
    }
}
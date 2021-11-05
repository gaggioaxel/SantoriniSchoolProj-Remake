package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.shared.color.Color;
import it.polimi.ingsw.shared.model.WorkerDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    Color colour;
    Worker w1;

    @Test
    void setColor() {
        w1 = new Worker();
        Color c = new Color("pink");
        w1.setColor(c);
        assertSame(c, w1.getColor());
    }

    @Test
    void getColor() {
        colour = new Color("pink");
        w1 = new Worker();
        w1.setColor(colour);
        assertSame(colour, w1.getColor());
    }

    @Test
    void testEquals() {
        w1 = new Worker();
        Worker w2 = new Worker();
        assertTrue(w1.equals(w1));
        assertFalse(w1.equals(w2));
    }

    @Test
    void testisHisWorker() {
        Worker w1 = new Worker();
        Player p2 = new Player("franco");
        Player p1 = new Player("gianni");
        assertFalse(p1.isHisWorker(w1));
        assertFalse(p1.isHisWorker(p2.getWorkers()[0]));
        assertFalse(p1.isHisWorker(p2.getWorkers()[1]));
        assertTrue(p1.isHisWorker(p1.getWorkers()[0]));
        assertTrue(p1.isHisWorker(p1.getWorkers()[1]));
    }

    @Test
    void getReadableCopy() {
        colour = new Color("pink");
        w1 = new Worker();
        w1.setColor(colour);
        WorkerDetails wd1 = new WorkerDetails(0, w1.getColor());
        assertNotSame(wd1, w1.getReadableCopy());
        assertSame(w1.getColor().getColorName(), wd1.getColor().getColorName());
    }
}
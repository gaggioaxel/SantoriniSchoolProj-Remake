package java.server.model.action;

import main.java.server.model.Worker;
import main.java.shared.model.Action;
import main.java.shared.model.BuildLevel;
import main.java.shared.utils.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {

    @Test
    void AIO() {
        Worker w = new Worker();
        Point f = new Point(0, 0);
        Point t = new Point(1, 1);
        Action forcedMove = new Action(w, Action.ActionType.MOVE, t, f, null, null);
        Action move = new Action(w, Action.ActionType.MOVE, f, t, forcedMove, null);
        assertTrue(move.getWorker().equals(w));
        assertEquals(move.getActionType(), Action.ActionType.MOVE);
        assertTrue(move.getFrom().equals(f));
        assertTrue(move.getTo().equals(t));
        assertTrue(move.hasForcedMove());
        assertTrue(move.getForcedMove().equals(forcedMove));
        assertTrue(move.equals(move));

        Action build = new Action(w, Action.ActionType.BUILD, f, t, null, BuildLevel.DOME);
        assertEquals(build.getActionType(), Action.ActionType.BUILD);
        assertEquals(build.getBuildLevel(), BuildLevel.DOME);
        assertTrue(build.equals(build));
    }

}
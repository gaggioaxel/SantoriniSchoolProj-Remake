package java.server.model.action;

import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.shared.model.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionsCacheTest {

    ActionsCache cache;

    @BeforeEach
    void setUp() {
        cache = new ActionsCache(1);
    }

    @Test
    void pushAction() {
        Player p1 = new Player("LoFiMeansLiFe");
        Action action = new Action(null, Action.ActionType.PASS, null, null, null, null);
        cache.pushAction(p1, action);
        assertTrue(cache.getDaMap().containsKey(p1) && cache.getDaMap().get(p1).get(0).equals(action));
        cache.pushAction(p1, action);
        assertTrue(cache.getDaMap().containsKey(p1) && cache.getDaMap().get(p1).get(1).equals(action));
    }

    @Test
    void clearCache() {
        Player p1 = new Player("LoFiMeansLiFe");
        Action action = new Action(null, Action.ActionType.PASS, null, null, null, null);
        cache.pushAction(p1, action);
        cache.clearCache(p1);
        assertNull(cache.getDaMap().get(p1));
    }

    @Test
    void getPlayerAction() {
        Player p1 = new Player("LoFiMeansLiFe");
        Action action = new Action(null, Action.ActionType.PASS, null, null, null, null);
        cache.pushAction(p1, action);
        assertTrue(cache.getPlayerAction(p1, Action.ActionType.PASS).equals(action));
        assertNull(cache.getPlayerAction(p1, Action.ActionType.MOVE));
    }

    @Test
    void getPlayerLastAction() {
        Player p1 = new Player("LoFiMeansLiFe");
        Action notLast = new Action(null, Action.ActionType.PASS, null, null, null, null);
        cache.pushAction(p1, notLast);
        Action LAST = new Action(null, Action.ActionType.MOVE, null, null, null, null);
        cache.pushAction(p1, LAST);
        assertTrue(cache.getPlayerLastAction(p1).equals(LAST) && !cache.getPlayerLastAction(p1).equals(notLast));
        Player p2 = new Player("DepressingMusic");
        assertNull(cache.getPlayerLastAction(p2));
    }

    @Test
    void removeLatestAction() {
        Player p1 = new Player("LoFiMeansLiFe");
        Action action = new Action(null, Action.ActionType.PASS, null, null, null, null);
        cache.pushAction(p1, action);
        Action whoAreYou = cache.removeLatestAction(p1);
        assertTrue(whoAreYou.equals(action));
        assertTrue(cache.getDaMap().get(p1).isEmpty());
    }
}
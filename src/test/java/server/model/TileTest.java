package java.server.model;

import main.java.server.model.Tile;
import main.java.server.model.Worker;
import main.java.shared.model.BuildLevel;
import main.java.shared.model.TileDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile tile;

    @BeforeEach
    void setup() {
        tile = new Tile();
    }

    @Test
    void getIntLevel() {
        assertEquals(0, tile.getIntLevel());
        tile.build(BuildLevel.LEVEL_1);
        assertEquals(1, tile.getIntLevel());
        tile.build(BuildLevel.LEVEL_2);
        assertEquals(2, tile.getIntLevel());
        tile.build(BuildLevel.LEVEL_3);
        assertEquals(3, tile.getIntLevel());
        tile.build(BuildLevel.DOME);
        assertEquals(4, tile.getIntLevel());
    }

    boolean assertSameBuildsLevel(List<BuildLevel> a, List<BuildLevel> b) {
        if(a == null && b == null)
            return true;
        if(a == null || b == null || a.size() != b.size())
            return false;


        boolean found;
        for(BuildLevel a1 : a) {
            found = false;
            for (BuildLevel b1 : b)
                if (b1.equals(a1)) {
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }

        return true;
    }

    @Test
    void testGetLevels() {
        LinkedList<BuildLevel> expected = new LinkedList<>();
        Random random = new Random();
        int buildLevel = random.nextInt(BuildLevel.values().length);
        expected.add(BuildLevel.GROUND);
        expected.add(BuildLevel.values()[buildLevel]);
        tile.build(BuildLevel.values()[buildLevel]);
        LinkedList<BuildLevel> actual = tile.getLevels();
        assertTrue(assertSameBuildsLevel(actual, expected));
    }

    @Test
    void testGetTopLevel() {
        BuildLevel expected = BuildLevel.DOME;
        tile.build(BuildLevel.LEVEL_1);
        tile.build(BuildLevel.LEVEL_2);
        tile.build(BuildLevel.LEVEL_3);
        tile.build(BuildLevel.DOME);
        BuildLevel actual = tile.getTopLevel();
        assertEquals(actual, expected);
    }

    @Test
    void getWorker() {
        Tile tile1 = new Tile();
        Worker w1 = new Worker();
        tile1.setWorker(w1);
        assertSame(w1, tile1.getWorker());
    }

    @Test
    void hasWorker() {
        Tile tile1 = new Tile();
        Worker w1 = new Worker();
        tile1.setWorker(w1);
        assertTrue(tile1.hasWorker());
        assertFalse(tile.hasWorker());
    }

    @Test
    void testRemoveWorker() {
        Worker worker = new Worker();
        tile.setWorker(worker);
        assertTrue(tile.hasWorker());
        tile.removeWorker();
        assertFalse(tile.hasWorker());
    }

    @Test
    void testSetWorker() {
        assertFalse(tile.hasWorker());
        Worker worker = new Worker();
        tile.setWorker(worker);
        assertTrue(tile.hasWorker());
        assertSame(worker, tile.getWorker());
    }

    @Test
    void testHasDome() {
        assertFalse(tile.hasDome());
        tile.build(BuildLevel.DOME);
        assertTrue(tile.hasDome());
    }

    @Test
    void testGetReadableCopy() {
        Worker worker = new Worker();
        ArrayList<BuildLevel> expectedBuild = new ArrayList<>();
        expectedBuild.add(BuildLevel.GROUND);
        TileDetails tileCopy = tile.getReadableCopy();
        assertTrue(assertSameBuildsLevel(tileCopy.getLevels(), expectedBuild));
        assertEquals(tile.hasWorker(), tileCopy.hasWorker());
        tile.setWorker(worker);
        tileCopy = tile.getReadableCopy();
        assertEquals(tile.hasWorker(), tileCopy.hasWorker());
    }
}
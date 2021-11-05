package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;
    ArrayList<Point> given;
    ArrayList<Point> expected;
    ArrayList<Point> actual;
    int DIM = 5;

    Point p00 = new Point(0,0);
    Point p01 = new Point(0,1);
    Point p02 = new Point(0,2);
    Point p03 = new Point(0,3);
    Point p04 = new Point(0,4);
    Point p10 = new Point(1,0);
    Point p11 = new Point(1,1);
    Point p12 = new Point(1,2);
    Point p13 = new Point(1,3);
    Point p14 = new Point(1,4);
    Point p20 = new Point(2,0);
    Point p21 = new Point(2,1);
    Point p22 = new Point(2,2);
    Point p23 = new Point(2,3);
    Point p24 = new Point(2,4);
    Point p30 = new Point(3,0);
    Point p31 = new Point(3,1);
    Point p32 = new Point(3,2);
    Point p33 = new Point(3,3);
    Point p34 = new Point(3,4);
    Point p40 = new Point(4,0);
    Point p41 = new Point(4,1);
    Point p42 = new Point(4,2);
    Point p43 = new Point(4,3);
    Point p44 = new Point(4,4);

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    boolean assertSameArrayLists(ArrayList<Point> a, ArrayList<Point> b) {
        if(a == null && b == null)
            return true;
        /*if(a == null || b == null || a.size() != b.size()) {
            return false;
        }*/


        boolean found;
        for(Point a1 : a) {
            found = false;
            for (Point b1 : b)
                if (b1.equals(a1)) {
                    //System.out.println("Found in to > "+b1.getX()+" "+b1.getY());
                    found = true;
                    break;
                }
            if(!found) {
                System.err.println("NOT FOUND IN ARRAY B > "+a1.getX()+" "+a1.getY());
                return false;
            }
        }

        for(Point b1 : b) {
            found = false;
            for(Point a1 : a)
                if(a1.equals(b1)) {
                    found = true;
                    break;
                }
            if(!found) {
                System.err.println("NOT FOUND IN ARRAY A > "+b1.getX()+" "+b1.getY());
                return false;
            }
        }

        return true;
    }

    boolean assertSamePoints(Point f, Point t) {
        if(f == null && t == null)
            return true;
        if(f == null || t == null)
            return false;
        return f.equals(t);
    }

    ArrayList<Point> clearDouble(ArrayList<Point> a) {
        ArrayList<Point> m = new ArrayList<>();
        boolean found = false;
        for(Point a1 : a) {
            for(Point m1 : m)
                if(m1.equals(a1)) {
                    found = true;
                    break;
                }
            if(!found)
                m.add(a1);
            found=false;
        }
        return m;
    }

    boolean assertSameBuildsLevel(LinkedList<BuildLevel> a, LinkedList<BuildLevel> b) {
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
    void testGetNearPoses() {
        expected = new ArrayList<>(8);
        expected.add(p10);
        expected.add(p01);
        expected.add(p11);
        actual = board.getNearPoses(p00, 1);
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>(8);
        expected.add(p00);
        expected.add(p20);
        expected.add(p01);
        expected.add(p11);
        expected.add(p21);
        actual = board.getNearPoses(p10, 1);
        assertTrue(assertSameArrayLists(actual, expected));


        expected = new ArrayList<>(8);
        expected.add(p30);
        expected.add(p31);
        expected.add(p41);
        actual = board.getNearPoses(p40, 1);
        assertTrue(assertSameArrayLists(actual, expected));


        expected = new ArrayList<>(8);
        expected.add(p00);
        expected.add(p20);
        expected.add(p01);
        expected.add(p11);
        expected.add(p21);
        actual = board.getNearPoses(p10, 1);
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>(8);
        expected.add(p11);
        expected.add(p21);
        expected.add(p31);
        expected.add(p12);
        expected.add(p32);
        expected.add(p13);
        expected.add(p23);
        expected.add(p33);
        actual = board.getNearPoses(p22, 1);
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>(24);
        expected.add(p00);
        expected.add(p10);
        expected.add(p20);
        expected.add(p30);
        expected.add(p40);
        expected.add(p01);
        expected.add(p11);
        expected.add(p21);
        expected.add(p31);
        expected.add(p41);
        expected.add(p02);
        expected.add(p12);
        expected.add(p32);
        expected.add(p42);
        expected.add(p03);
        expected.add(p13);
        expected.add(p23);
        expected.add(p33);
        expected.add(p43);
        expected.add(p04);
        expected.add(p14);
        expected.add(p24);
        expected.add(p34);
        expected.add(p44);
        actual = board.getNearPoses(p22, 2);
        assertTrue(assertSameArrayLists(actual, expected));

    }

    @Test
    void testGetNextLinedUpPos() {
        Point expectedP = p30;
        Point actualP = board.getNextLinedUpPos(p10, p20);
        assertTrue(assertSamePoints(actualP, expectedP));
        assertNull(board.getNextLinedUpPos(p10, p00));
        expectedP = p22;
        actualP = board.getNextLinedUpPos(p00, p11);
        assertTrue(assertSamePoints(actualP, expectedP));
    }

    @Test
    void testDeltaLevel() {
        board.getTile(p00).build(BuildLevel.LEVEL_1);
        assertEquals(board.getDeltaLevel(p00, p01), 1);
        assertEquals(board.getDeltaLevel(p10, p00), -1);
    }

    @Test
    void testApplyDomeFilter() {
        board.getTile(p00).build(BuildLevel.DOME);
        expected = new ArrayList<>();
        given = new ArrayList<>();
        given.add(p00);
        actual = board.applyDomeFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testDontApplyDomeFilter() {
        given = new ArrayList<>();
        given.add(p00);
        expected = new ArrayList<>(given);
        actual = board.applyDomeFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testApplyWorkersFilter(){
        board.getTile(p00).setWorker(new Worker());
        expected = new ArrayList<>();

        given = new ArrayList<>();
        given.add(p00);
        actual = board.applyWorkersFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testDontApplyWorkerFilter() {
        expected = new ArrayList<>();
        expected.add(p00);
        given = new ArrayList<>(expected);
        actual = board.applyWorkersFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testApplyMoreOneStepUpFilter() {
        board.getTile(p10).build(BuildLevel.LEVEL_2);
        expected = new ArrayList<>();
        given = new ArrayList<>();
        given.add(p10);
        actual = board.applyMoreOneStepUpFilter(p00, given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testDontApplyMoreOneStepUpFilter() {
        expected = new ArrayList<>();
        expected.add(p10);
        given = new ArrayList<>(expected);
        actual = board.applyMoreOneStepUpFilter(p00, given);
        assertTrue(assertSameArrayLists(actual, expected));
        board.getTile(p10).build(BuildLevel.LEVEL_1);
        actual = board.applyMoreOneStepUpFilter(p00, given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testApplyPerimeterFilter() {
        expected = new ArrayList<>();
        given = new ArrayList<>();
        given.add(p00);
        actual = board.applyPerimeterFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testDontApplyPerimeterFilter() {
        expected = new ArrayList<>();
        expected.add(p11);
        given = new ArrayList<>(expected);
        actual = board.applyPerimeterFilter(given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testGetLinkedPerimeterAndNear_WithoutLimiter() {
        expected = new ArrayList<>();
        actual = board.getLinkedPerimeterAndNear(null, p11, null, new ArrayList<>(1));
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>();
        expected.add(p00);
        expected.add(p10);
        expected.add(p20);
        expected.add(p30);
        expected.add(p40);
        expected.add(p01);
        expected.add(p11);
        expected.add(p21);
        expected.add(p31);
        expected.add(p41);
        expected.add(p02);
        expected.add(p12);
        expected.add(p32);
        expected.add(p42);
        expected.add(p03);
        expected.add(p13);
        expected.add(p23);
        expected.add(p33);
        expected.add(p43);
        expected.add(p04);
        expected.add(p14);
        expected.add(p24);
        expected.add(p34);
        expected.add(p44);
        Point givenP = p00;

        //basic scenario, everything clear
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //got every point except the middle point

        //level 2 in one border point
        board.getTile(p20).build(BuildLevel.LEVEL_2);
        expected.remove(p20);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //got every point except the one in the border

        //level 2 in a border and a corner
        board.getTile(p40).build(BuildLevel.LEVEL_2);
        expected.remove(p40);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //got every point except the border and corner

        board.getTile(p20).build(BuildLevel.GROUND);
        board.getTile(p40).build(BuildLevel.GROUND);

        //level 2 in two border locations
        board.getTile(p10).build(BuildLevel.LEVEL_2);
        board.getTile(p30).build(BuildLevel.LEVEL_2);
        expected.add(p40);
        expected.remove(p10);
        expected.remove(p20);
        expected.remove(p21);
        expected.remove(p30);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //got every point except those between two borders point

        board.getTile(p10).build(BuildLevel.GROUND);
        board.getTile(p30).build(BuildLevel.GROUND);

        //level 2 in the two perimeter poses near
        board.getTile(p10).build(BuildLevel.LEVEL_2);
        board.getTile(p01).build(BuildLevel.LEVEL_2);
        expected.clear();
        expected.add(p11);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //got only out of border

        //level 2 all around
        board.getTile(p11).build(BuildLevel.LEVEL_2);
        expected.remove(p11);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //empty

        //level 2, clockwise, near starting point and near outside perimeter, and counterclockwise at one step of distance
        board.getTile(p10).build(BuildLevel.GROUND);
        board.getTile(p20).build(BuildLevel.LEVEL_2);
        expected.add(p00);
        expected.add(p10);
        expected.add(p21);
        actual = board.getLinkedPerimeterAndNear(null, givenP, null, new ArrayList<>(1));
        actual = clearDouble(actual);
        assertTrue(assertSameArrayLists(actual, expected));
        //(starting pos) (near) and (near-outside perimeter)

        //starting from outside perimeter
        actual = board.getLinkedPerimeterAndNear(null, p11, null, new ArrayList<>(1));
        expected.clear();
        assertTrue(assertSameArrayLists(actual, expected));
        //gets an empty list

    }

    @Test
    void testInsertUnderYourFeetPos() {
        expected = new ArrayList<>();
        expected.add(p00);
        given = new ArrayList<>();
        actual = board.insertUnderYourFeetPos(p00, given);
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>();
        expected.add(p00);
        expected.add(p10);
        expected.add(p01);
        expected.add(p11);
        given = new ArrayList<>(expected);
        given.remove(0);
        actual = board.insertUnderYourFeetPos(p00, given);
        assertTrue(assertSameArrayLists(actual, expected));

        expected = new ArrayList<>();
        expected.add(p33);
        expected.add(p43);
        expected.add(p34);
        expected.add(p44);
        given = new ArrayList<>(expected);
        given.remove(3);
        actual = board.insertUnderYourFeetPos(p44, given);
        assertTrue(assertSameArrayLists(actual, expected));
    }

    @Test
    void testIsPerimeter() {
        assertTrue(board.isPerimeter(p00));
        assertTrue(board.isPerimeter(p30));
        assertFalse(board.isPerimeter(p22));
    }

    @Test
    void testIsOutOfBounds() {
        assertFalse(board.isOutOfBounds(p11));
        assertTrue(board.isOutOfBounds(new Point(-1, 1)));
        assertTrue(board.isOutOfBounds(new Point(1, -1)));
        assertTrue(board.isOutOfBounds(new Point(-42, -42)));
    }

    @Test
    void getWorkerPos() {
        Worker w1 = new Worker();
        Random random = new Random();
        int x = random.nextInt(DIM);
        int y = random.nextInt(DIM);
        board.getTile(new Point(x,y)).setWorker(w1);
        Point expectedP = new Point(x, y);
        Point actualP = board.getWorkerPos(w1);
        assertTrue(assertSamePoints(actualP, expectedP));
    }

    @Test
    void getReadableCopy() {
        Random random = new Random();
        LinkedList<BuildLevel>[][] expectedBuilds = new LinkedList[DIM][DIM];
        Worker[][] expectedWorker = new Worker[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                int buildTimes = random.nextInt(BuildLevel.values().length-1);
                board.getTile(new Point(j, i)).build(BuildLevel.values()[buildTimes]);
                board.getTile(new Point(j, i)).setWorker(random.nextBoolean() ? new Worker() : null);
                expectedBuilds[i][j] = board.getTile(new Point(j, i)).getLevels();
                expectedWorker[i][j] = board.getTile(new Point(j, i)).getWorker();
            }
        }

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                assertTrue(assertSameBuildsLevel(expectedBuilds[i][j], new LinkedList<>(board.getReadableCopy().getTile(i,j).getLevels())));
                assertEquals(expectedWorker[i][j]== null, board.getReadableCopy().getTile(i,j).getWorker()==null);
            }
        }

    }
}
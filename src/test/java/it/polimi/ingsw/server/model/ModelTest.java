package it.polimi.ingsw.server.model;

import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.server.model.turn.PassiveEffect;
import it.polimi.ingsw.server.model.turn.movement.limiter.CantMoveUpLimiter;
import it.polimi.ingsw.server.model.turn.strategy.TurnPhase;
import it.polimi.ingsw.shared.utils.Triplet;
import it.polimi.ingsw.shared.color.Color;
import it.polimi.ingsw.shared.model.BuildLevel;
import it.polimi.ingsw.shared.model.CardDetails;
import it.polimi.ingsw.shared.utils.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Model model;

    boolean assertContains3(List<Action> a, Action b) {
        for(Action a1 : a)
            if(a1.equals(b))
                return true;
        return false;
    }

    boolean assertSameList1(List<CardDetails> a, List<CardDetails> b) {
        boolean found;
        for (CardDetails a1 : a) {
            found = false;
            for (CardDetails b1 : b)
                if (b1.getCardName().equals(a1.getCardName())) {
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        for (CardDetails b1 : b) {
            found = false;
            for (CardDetails a1 : a)
                if (b1.getCardName().equals(a1.getCardName())) {
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        return true;
    }

    boolean assertSameList2(List<Point> a, List<Point> b) {
        boolean found=false;
        for (Point e : a) {
            for (Point el : b)
                if (el.equals(e)) {
                    found = true;
                    break;
                }
            if(!found)
                return false;
            else
                found = false;
        }
        found=false;
        for (Point e : b) {
            for (Point el : a)
                if (el.equals(e)) {
                    found = true;
                    break;
                }
            if(!found)
                return false;
            else
                found = false;
        }
        return true;
    }

    boolean assertSameList3(List<Action> a, List<Action> b) {
        boolean found=false;
        for (Action e : a) {
            for (Action el : b)
                if (el.equals(e)) {
                    found = true;
                    break;
                }
            if(!found) {
                System.err.println("NOT FOUND IN ARRAY B > "+e.getTo().getX()+" "+e.getTo().getY());
                return false;
            }
            else
                found = false;
        }
        found=false;
        for (Action e : b) {
            for (Action el : a)
                if (el.equals(e)) {
                    found = true;
                    break;
                }
            if(!found) {
                System.err.println("NOT FOUND IN ARRAY A > "+e.getFrom().getX()+" "+e.getFrom().getY()+" -> "+e.getTo().getX()+" "+e.getTo().getY());
                return false;
            }
            else
                found = false;
        }
        return true;
    }

    @BeforeEach
    void setUp() {
        model = new Model();
    }

    @Test
    void setterAndGetter() {
        model.setUsername("Gaggio");
        assertTrue(new Player("Gaggio").equals(model.getPlayer("Gaggio")));
        model.setNumPlayer(2);
        assertEquals(model.getNumbOfPlayers(), 2);
    }

    @Test
    void setPlayerColor() {
        Player p = new Player("PercivalCox");
        model.setPlayerColor(p, "pink");
        assertTrue(p.getColor().equals(new Color("pink")));
    }

    @Test
    void selectCardsFromDeck() {
        Player p = new Player("Janitor");
        model.selectCardsFromDeck(new int[]{0, 1, 2});
        ArrayList<CardDetails> actual = model.getHand();
        ArrayList<CardDetails> expected = new ArrayList<>();
        expected.add(model.getDeck().getCard("Apollo"));
        expected.add(model.getDeck().getCard("Artemis"));
        expected.add(model.getDeck().getCard("Athena"));
        assertTrue(assertSameList1(expected, actual));
    }

    @Test
    void pickACard() throws IOException {
        Player p1 = new Player("JD");
        model.selectCardsFromDeck(new int[]{0, 1, 2});
        assertFalse(model.getPlayersCardTurn().containsKey(p1));
        assertFalse(model.getPlayersPassiveEffect().containsKey(p1));
        assertFalse(model.getPlayerStdTurnVariations().containsKey(p1));
        assertFalse(model.getPlayerChosenTurnStrategy().containsKey(p1));
        model.playerPicksACard(p1, 1); //Artemis
        assertTrue(p1.getCard().equals(new Deck().getCard("Artemis")));
        assertTrue(model.getPlayersPassiveEffect().isEmpty());

        Player p2 = new Player("Jordan");
        model.selectCardsFromDeck(new int[] {0, 1, 2});
        model.playerPicksACard(p2, 2); //Athena
        assertTrue(p2.getCard().equals(new Deck().getCard("Athena")));
        assertTrue(model.getPlayersPassiveEffect().containsKey(p2));

        Player p3 = new Player("NeedSushi");
        model.selectCardsFromDeck(new int[] {6});
        model.playerPicksACard(p3, 0); //Hera
        assertTrue(p3.getCard().equals(new Deck().getCard("Hera")));
        assertTrue(model.getPlayersWinLimiter().containsKey(p3));

        Player p4 = new Player("RightNow");
        model.selectCardsFromDeck(new int[] {9}); //Pan
        model.playerPicksACard(p4, 0);
        assertTrue(p4.getCard().equals(new Deck().getCard("Pan")));
        assertTrue(model.getPlayersWinConditions().containsKey(p4) && model.getPlayersWinConditions().get(p4).size()==2);

    }

    @Test
    void placeWorker() {
        Player p = new Player("Elliot");
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p, 0);
        assertTrue(model.getBoard().getWorkerPos(p.getWorkers()[0]).equals(new Point(0, 0)));
        model.placeWorker(p, 0);
        assertTrue(model.getBoard().getWorkerPos(p.getWorkers()[1]).equals(new Point(1, 0)));
    }

    @Test
    void undoLastPlacement() {
        Player p = new Player("Ted");
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p, 0);
        model.placeWorker(p, 0);
        ArrayList<Point> expected = new ArrayList<>(model.getWorkersPlacementAllowedPoses());
        expected.add(new Point(0, 0));
        expected.add(new Point(1, 0));
        model.undoLastPlacement(p);
        model.undoLastPlacement(p);
        assertTrue(assertSameList2(expected, model.getWorkersPlacementAllowedPoses()));
    }

    @Test
    void showActions() throws IOException {
        Player p1 = new Player("Turk");
        Player p2 = new Player("Carla");
        model.selectCardsFromDeck(new int[] {0, 1, 2});
        model.playerPicksACard(p1, 0); //picks apollo
        model.playerPicksACard(p2, 1); //picks athena
        model.showAvailablePlacesForWorkersPos(); //creates array of poses
        model.placeWorker(p1, 0); //should be 0 0
        model.placeWorker(p1, 0); //should be 1 0
        model.placeWorker(p2, 0); //should be 2 0
        model.placeWorker(p2, 0); //should be 3 0
        model.showActions(p1);
        ArrayList<Action> expected = model.getDeck().getCard("Apollo").getTurn().pollNextPhase().y.getActions(p1, model.getBoard(), null, new ArrayList<>(1), new ArrayList<>(1));
        ArrayList<Action> actual = model.getLatestAllowedActions();

        assertTrue(assertSameList3(expected, actual));
        assertFalse(model.getCurrPlayerHasLost());
        assertEquals(model.getIndexFirstStandardTurnAction(), -1);

        model = new Model();
        Player p3 = new Player("Laverne");
        model.selectCardsFromDeck(new int[]{11}); //Prometheus
        model.playerPicksACard(p3, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p3, 0); // 0 0
        model.placeWorker(p3, 1); // 2 0
        model.showActions(p3);
        Worker w1 = p3.getWorkers()[0];
        Worker w2 = p3.getWorkers()[1];
        actual = model.getLatestAllowedActions();
        expected = new ArrayList<>(); //order or size doesn't matter
        Point from1 = new Point(0, 0);
        Point from2 = new Point(2, 0);
        expected.add(new Action(w1, Action.ActionType.BUILD, from1, new Point(1, 0), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w1, Action.ActionType.BUILD, from1, new Point(0, 1), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w1, Action.ActionType.BUILD, from1, new Point(1, 1), null, BuildLevel.LEVEL_1));

        expected.add(new Action(w1, Action.ActionType.MOVE, from1, new Point(1, 0), null, null));
        expected.add(new Action(w1, Action.ActionType.MOVE, from1, new Point(0, 1), null, null));
        expected.add(new Action(w1, Action.ActionType.MOVE, from1, new Point(1, 1), null, null));

        expected.add(new Action(w2, Action.ActionType.BUILD, from2, new Point(1, 0), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w2, Action.ActionType.BUILD, from2, new Point(3, 0), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w2, Action.ActionType.BUILD, from2, new Point(1, 1), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w2, Action.ActionType.BUILD, from2, new Point(2, 1), null, BuildLevel.LEVEL_1));
        expected.add(new Action(w2, Action.ActionType.BUILD, from2, new Point(3, 1), null, BuildLevel.LEVEL_1));

        expected.add(new Action(w2, Action.ActionType.MOVE, from2, new Point(1, 0), null, null));
        expected.add(new Action(w2, Action.ActionType.MOVE, from2, new Point(3, 0), null, null));
        expected.add(new Action(w2, Action.ActionType.MOVE, from2, new Point(1, 1), null, null));
        expected.add(new Action(w2, Action.ActionType.MOVE, from2, new Point(2, 1), null, null));
        expected.add(new Action(w2, Action.ActionType.MOVE, from2, new Point(3, 1), null, null));

        assertTrue(assertSameList3(expected, actual));

        model = new Model();
        Player p4 = new Player("NamesFinished");
        model.selectCardsFromDeck(new int[]{12}); //Triton
        model.playerPicksACard(p4, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p4, 0); // 0 0
        model.placeWorker(p4, 1); // 2 0
        w1 = p4.getWorkers()[0];
        w2 = p4.getWorkers()[1];

        //inject a passive effect inactive
        ArrayList<Triplet<TurnPhase, PassiveEffect, AtomicBoolean>> mock = new ArrayList<>(1);
        mock.add(new Triplet<>(new TurnPhase(TurnPhase.TurnPhaseType.MOVE, false), new CantMoveUpLimiter(CantMoveUpLimiter.PassiveEffectTarget.ENEMIES), new AtomicBoolean(false)));
        model.getPlayersPassiveEffect().put(p3, mock);
        model.getCache().pushAction(p3, new Action(p3.getWorkers()[0], Action.ActionType.MOVE, new Point(3,4), new Point(4, 4), null, null));
        model.getBoard().getTile(new Point(4, 4)).build(BuildLevel.LEVEL_1);
        model.getBoard().getTile(new Point(4,4)).setWorker(p3.getWorkers()[0]);

        model.showActions(p4);
        expected.clear();
        for(int y=0; y<5; y++)
            for (int x=0; x<5; x++) {
                if((x!=2 || y!=0) && (x!=2 || y!=2) && (x!=4 || y!=4))
                    expected.add(new Action(w1, Action.ActionType.MOVE, new Point(0, 0), new Point(x, y), null, null));
                if((x!=0 || y!=0) && (x!=2 || y!=2) && (x!=4 || y!=4))
                    expected.add(new Action(w2, Action.ActionType.MOVE, new Point(2, 0), new Point(x, y), null, null));
            }
        actual = model.getLatestAllowedActions();
        assertTrue(assertSameList3(actual, expected));


        model = new Model();
        model.selectCardsFromDeck(new int[]{8, 9});
        model.playerPicksACard(p1, 0);
        model.playerPicksACard(p2, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 20);// somewhere far away
        model.placeWorker(p2, 0); // 1 0
        model.placeWorker(p2, 17);// far away
        model.showActions(p1);
        actual = model.getLatestAllowedActions();
        assertEquals(8, actual.size());

        model = new Model();
        model.selectCardsFromDeck(new int[]{1, 9});
        model.playerPicksACard(p1, 0);
        model.playerPicksACard(p2, 0);
        model.showAvailablePlacesForWorkersPos();
        //stuck one worker in an angle, moves are 5
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p2,0); // 1 0
        model.placeWorker(p2, 3); // 0 1
        model.placeWorker(p1, 9); // 2 2
        //place the second in the middle, size of first player moves should be 25 - 3 = 22
        model.showActions(p1);
        actual = model.getLatestAllowedActions();
        assertEquals(25, actual.size());

        model = new Model();
        model.selectCardsFromDeck(new int[]{13});
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 23); // 4 4
        model.showActions(p1);
        actual = model.getLatestAllowedActions();
        assertEquals(6, actual.size());
        /*model.performAction(p1, 0);
        model.showActions(p1);
        actual = model.getLatestAllowedActions();
        Action act = new Action(p1.getWorkers()[0], Action.ActionType.BUILD, new Point(1,1), new Point(0,2), null, BuildLevel.LEVEL_1);
        for(Action a : actual)
            System.out.println("build "+a.getActionType()+" from "+a.getFrom().getX()+" "+a.getFrom().getY()+" to "+a.getTo().getX()+" "+a.getTo().getY());
        assertTrue(actual.get(5).equals(act));*/

    }

    @Test
    void private_getTurn() {
        Player p1 = new Player("Todd");
        model.selectCardsFromDeck(new int[]{0, 1, 2});
        model.playerPicksACard(p1, 0);
        assertEquals(model.getGetTurn(p1), p1.getCard().getTurn());
    }

    @Test
    void performAction() {
        // Standard Turn Testing
        Player p1 = new Player("Laverne");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        Worker w1 = p1.getWorkers()[0];
        ArrayList<Action> actions = model.getLatestAllowedActions();
        model.performAction(p1, 0);
        Board b = model.getBoard();
        assertTrue(!b.getTile(actions.get(0).getFrom()).hasWorker() &&
                b.getTile(actions.get(0).getTo()).hasWorker() &&
                b.getTile(actions.get(0).getTo()).getWorker().equals(w1));
        assertNotNull(model.getCache().getPlayerAction(p1, actions.get(0).getActionType()));

        model.showActions(p1);
        actions = model.getLatestAllowedActions();
        model.performAction(p1, 0);
        assertSame(b.getTile(actions.get(0).getTo()).getTopLevel(), actions.get(0).getBuildLevel());
        assertNotNull(model.getCache().getPlayerAction(p1, actions.get(0).getActionType()));

        model.rollbackTurnActions(p1, true);
        model.undoCardSelection(p1);

        // Different Turn Testing
        model.selectCardsFromDeck(new int[]{11}); //Prometheus
        model.playerPicksACard(p1, 0);
        model.showActions(p1);
        ArrayList<Action> actions1 = model.getLatestAllowedActions();
        assertEquals(8, model.getIndexFirstStandardTurnAction()); //3 for worker1 in the top-left corner, 5 for worker2 in (2 0)
        assertTrue(actions1.get(model.getIndexFirstStandardTurnAction()-1).getActionType().equals(Action.ActionType.BUILD) &&
                actions1.get(model.getIndexFirstStandardTurnAction()).getActionType().equals(Action.ActionType.MOVE));
        model.performAction(p1, 0);
        model.showActions(p1);
        actions1 = model.getLatestAllowedActions();
        model.performAction(p1, 0);
        assertTrue(model.getBoard().getTile(actions1.get(0).getTo()).getWorker().equals(actions1.get(0).getWorker()));

        // Optional Phases Testing
        model = new Model();
        model.selectCardsFromDeck(new int[]{4}); //Demeter
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        Action move = model.getLatestAllowedActions().get(0);
        model.performAction(p1, 0);
        model.showActions(p1);
        Action build = model.getLatestAllowedActions().get(0);
        model.performAction(p1, 0);
        model.showActions(p1);
        ArrayList<Action> actions2 = model.getLatestAllowedActions();
        int passIndx = 0;
        for(Action a : actions2)
            if(a.getActionType().equals(Action.ActionType.PASS))
                passIndx = actions2.indexOf(a);
        model.performAction(p1, passIndx);
        assertTrue(model.isTurnEnded(p1));
    }

    @Test
    void hasPlayerJustMoved() {
        Player p1 = new Player("Laverne");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        model.performAction(p1, 0);
        assertTrue(model.hasPlayerJustMoved(p1));
    }

    @Test
    void hasPlayerWon() {
        Player p1 = new Player("Laverne");
        model.selectCardsFromDeck(new int[]{9}); //Pan
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        model.getBoard().getTile(new Point(1, 0)).build(BuildLevel.LEVEL_3);
        model.getLatestAllowedActions().add(0, new Action(p1.getWorkers()[0], Action.ActionType.MOVE, new Point(0, 0), new Point(1, 0), null, null));
        model.performAction(p1, 0);
        assertTrue(model.hasPlayerWon(p1));

        model.getLatestAllowedActions().add(0, new Action(p1.getWorkers()[0], Action.ActionType.MOVE, new Point(1, 0), new Point(0, 0), null, null));
        model.getCache().clearCache(p1);
        model.performAction(p1, 0);
        assertTrue(model.hasPlayerWon(p1));

        model.getLatestAllowedActions().add(0, new Action(p1.getWorkers()[0], Action.ActionType.MOVE, new Point(0, 0), new Point(0, 1), null, null));
        model.getCache().clearCache(p1);
        model.performAction(p1, 0);
        assertFalse(model.hasPlayerWon(p1));

    }

    @Test
    void isTurnEnded() {
        Player p1 = new Player("Molly");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        assertFalse(model.isTurnEnded(p1));
        model.performAction(p1, 0);
        model.showActions(p1);
        model.performAction(p1, 0);
        assertTrue(model.isTurnEnded(p1));
    }

    @Test
    void proceedNexTurn() {
        Player p1 = new Player("Doug");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        model.performAction(p1, 0);
        model.showActions(p1);
        model.performAction(p1, 0);
        model.proceedNexTurn(p1);

        assertFalse(model.getPlayerChosenTurnStrategy().containsKey(p1));
    }

    @Test
    void rollbackTurnActions() {
        Player p1 = new Player("Sean");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        ArrayList<Action> actions1 = model.getLatestAllowedActions();
        model.performAction(p1, 0);
        model.showActions(p1);
        ArrayList<Action> actions2 = model.getLatestAllowedActions();
        model.performAction(p1, 0);
        model.rollbackTurnActions(p1, true);
        assertTrue(!model.getBoard().getTile(actions1.get(0).getTo()).hasWorker() &&
                model.getBoard().getTile(actions1.get(0).getFrom()).hasWorker() &&
                model.getBoard().getTile(actions2.get(0).getTo()).getTopLevel().equals(BuildLevel.GROUND));
    }

    @Test
    void removePlayer() {
        Player p1 = new Player("Denise");
        model.selectCardsFromDeck(new int[]{0}); //Apollo
        model.playerPicksACard(p1, 0);
        model.showAvailablePlacesForWorkersPos();
        model.placeWorker(p1, 0); // 0 0
        model.placeWorker(p1, 1); // 2 0
        model.showActions(p1);
        model.performAction(p1, 0);
        model.showActions(p1);
        model.performAction(p1, 0);
        model.removePlayer(p1);
        assertTrue(!model.getPlayerChosenTurnStrategy().containsKey(p1) && model.getBoard().getWorkerPos(p1.getWorkers()[0])==null &&
                model.getBoard().getWorkerPos(p1.getWorkers()[1])==null);

    }

    @Test
    void hasPlayerLost() {
        Player p1 = new Player("Finally");
        model.setCurrPlayerHasLost();
        assertTrue(model.hasPlayerLost(p1));
    }
}
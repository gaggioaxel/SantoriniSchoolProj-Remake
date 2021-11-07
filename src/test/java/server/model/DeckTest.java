package java.server.model;

import main.java.server.model.Card;
import main.java.server.model.Deck;
import main.java.shared.model.CardDetails;
import main.java.shared.model.DeckDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;
    ArrayList<CardDetails> expected;

    @BeforeEach
    void setUp() throws IOException {
        deck  = new Deck();
        expected = new ArrayList<>();
        expected.add(deck.getCard("Apollo"));
        expected.add(deck.getCard("Artemis"));
        expected.add(deck.getCard("Athena"));
        expected.add(deck.getCard("Atlas"));
        expected.add(deck.getCard("Demeter"));
        expected.add(deck.getCard("Hephaestus"));
        expected.add(deck.getCard("Hera"));
        expected.add(deck.getCard("Hestia"));
        expected.add(deck.getCard("Minotaur"));
        expected.add(deck.getCard("Pan"));
        expected.add(deck.getCard("Poseidon"));
        expected.add(deck.getCard("Prometheus"));
        expected.add(deck.getCard("Triton"));
        expected.add(deck.getCard("Zeus"));
    }

    @Test
    void getAllCardsName() {
        ArrayList<CardDetails> actual = deck.getAllCardsDetails();
        assertTrue(assertSameList(expected, actual));
    }

    @Test
    void getCard() {
        Card ap = new Card("Apollo", null);
        Card ap1 = deck.getCard("Apollo");
        assertTrue(ap.equals(ap1));
        assertTrue(ap1.getTurn()!=null && ap1.getWinChecker()!=null);
    }

    @Test
    void getReadableCopy() {
        try {
            DeckDetails dd = new DeckDetails(expected);
            DeckDetails actual = deck.getReadableCopy();
            assertTrue(assertSameList(dd.getAllCardsDetails(), actual.getAllCardsDetails()));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    boolean assertSameList(List<CardDetails> a, List<CardDetails> b) {
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

    boolean assertSameSet(Set<CardDetails> a, Set<CardDetails> b) {
        return assertSameList(new ArrayList<>(a), new ArrayList<>(b));
    }
}
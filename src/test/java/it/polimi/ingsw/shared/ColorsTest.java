package it.polimi.ingsw.shared;

import it.polimi.ingsw.shared.color.Colors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorsTest {
    private Colors colors;
    private int r;
    private int g;
    private int b;


    @Test
    void getRed() {
        colors = Colors.pink;
        int r = colors.getRed();
        assertEquals(255,r);
    }

    @Test
    void getGreen() {
        colors = Colors.orange;
        int g = colors.getGreen();
        assertEquals(127,g);
    }

    @Test
    void getBlue() {
        colors = Colors.purple;
        int b = colors.getBlue();
        assertEquals(255,b);
    }
}
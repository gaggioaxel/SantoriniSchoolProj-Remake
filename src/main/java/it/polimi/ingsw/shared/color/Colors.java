package it.polimi.ingsw.shared.color;

import java.io.Serializable;

public enum Colors implements Serializable {
    pink (255, 0, 127),
    purple (127, 0, 255),
    orange (255, 127, 0);

    private final int r;
    private final int g;
    private final int b;

    Colors(int red, int green, int blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    public int getRed() {
        return this.r;
    }

    public int getGreen() {
        return this.g;
    }

    public int getBlue() {
        return this.b;
    }

}

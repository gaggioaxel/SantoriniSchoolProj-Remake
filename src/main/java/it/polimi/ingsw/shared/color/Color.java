package it.polimi.ingsw.shared.color;

import it.polimi.ingsw.shared.utils.Triplet;

import java.io.Serializable;

/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */
public class Color implements Serializable {

    private final String colorName;
    private final Triplet<Integer, Integer, Integer> rgbValues;


    public Color(String colorName) {
        this.colorName = colorName;
        rgbValues = new Triplet<>(Colors.valueOf(colorName).getRed(), Colors.valueOf(colorName).getGreen(), Colors.valueOf(colorName).getBlue());
    }

    /**
     * getter
     * @return color name
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * getter
     * @return the triplet of <red, green, blue> values
     */
    public Triplet<Integer, Integer, Integer> getRGBValues() {
        return this.rgbValues;
    }

    /**
     * checks if the color is the same
     * @param c to compare
     * @return true if they have the same rgb values
     */
    public boolean equals(Color c) {
        return rgbValues.equals(c.rgbValues);
    }
}
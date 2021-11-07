package main.java.shared.model;

import main.java.shared.color.Color;

import java.io.Serializable;

public class WorkerDetails implements Serializable {

    protected int ID;
    protected Color color;

    public WorkerDetails(int ID, Color color) {
        this.ID = ID;
        this.color = color;
    }

    protected WorkerDetails(){}

    protected void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter
     * @return the color of this worker if set
     */
    public Color getColor() {
        return color;
    }

    /**
     * compares the ids
     * @param other worker to check
     * @return true if ids match
     */
    public boolean equals(WorkerDetails other) {
        return ID == other.ID;
    }
}

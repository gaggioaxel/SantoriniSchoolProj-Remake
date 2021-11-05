package it.polimi.ingsw.server.model;

import it.polimi.ingsw.shared.color.Color;
import it.polimi.ingsw.shared.model.WorkerDetails;

/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */
public class Worker extends WorkerDetails {

    private static int count=0;
    private static final Object lock = new Object();


    /**
     * creates a worker with a unique id
     */
    public Worker() {
        synchronized (lock) {
            super.ID = count;
            count++;
        }
    }

    /**
     * setter
     * @param color to set
     */
    public void setColor(Color color) {
        super.setColor(color);
    }

    /**
     * getter
     * @return color of this worker
     */
    public Color getColor() {
        return color;
    }

    public WorkerDetails getReadableCopy() {
        return new WorkerDetails(ID, color);
    }

    /**
     * checks if both workers have the same ids
     * @param worker to check
     * @return true if they have the same id
     */
    public boolean equals(it.polimi.ingsw.server.model.Worker worker) {
        return worker!=null && ID == worker.ID;
    }

    public boolean equals(WorkerDetails worker) {
        return worker != null && worker.equals(getReadableCopy());
    }

}
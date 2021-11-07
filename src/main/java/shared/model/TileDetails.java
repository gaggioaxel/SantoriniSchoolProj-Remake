package main.java.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class TileDetails implements Serializable {

    private final LinkedList<BuildLevel> levels;
    private final WorkerDetails worker;

    /**
     * creates a read only version of a tile
     * @param levels of the tile
     * @param worker that is present on the tile
     */
    public TileDetails(LinkedList<BuildLevel> levels, WorkerDetails worker) {
        this.levels = levels;
        this.worker = worker;
    }

    /**
     * getter
     * @return a read only version of a worker
     */
    public WorkerDetails getWorker() {
        return worker;
    }

    /**
     * checks if this tile has a worker
     * @return true if has a worker
     */
    public boolean hasWorker() {
        return worker!=null;
    }

    /**
     * getter
     * @return a copy of the levels of this tile
     */
    public ArrayList<BuildLevel> getLevels() {
        return new ArrayList<>(levels);
    }

}

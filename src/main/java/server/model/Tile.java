package main.java.server.model;

import main.java.shared.model.BuildLevel;
import main.java.shared.model.TileDetails;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */
public class Tile {

    private final LinkedList<BuildLevel> levels = new LinkedList<>();
    private Worker worker = null;


    public Tile() {
        this.levels.push(BuildLevel.GROUND);
    }

    /**
     * gets the ordinal number of the top level
     * @return the ordinal number of top level
     */
    public int getIntLevel() {
        return levels.peek().ordinal();
    }

    /**
     * getter
     * @return a linked list of all the levels
     */
    public LinkedList<BuildLevel> getLevels() {
        return new LinkedList<>(levels);
    }


    /**
     * getter
     * @return the top level of the building
     */
    public BuildLevel getTopLevel() {
        return levels.peek();
    }

    /**
     * removes the top level
     */
    public void removeTopLevel() {
        levels.pop();
    }

    /**
     * getter
     * @return the worker in this tile
     */
    public Worker getWorker() {
        return this.worker;
    }

    /**
     * checks if there's a worker in this tile
     * @return true if there's a worker
     */
    public boolean hasWorker() {
        return this.worker != null;
    }

    /**
     * retrieves and remove the worker present in this tile
     * @return the worker present or null
     */
    public Worker removeWorker() {
        Worker worker = this.worker;
        this.worker = null;
        return worker;
    }

    /**
     * setter
     * @param worker to set
     */
    public void setWorker(Worker worker){
        this.worker = worker;
    }

    /**
     * checks if there's a dome on top
     * @return true if the top level equals to a dome
     */
    public boolean hasDome() {
        return Objects.equals(levels.peek(), BuildLevel.DOME);
    }

    /**
     * pushes in stack of build levels
     * @param level is the level to push on top
     */
    public void build(BuildLevel level) {
        levels.push(level);
    }

    /**
     * creates a read only version of this tile
     * @return a tile copy with all the details
     */
    public TileDetails getReadableCopy() {
        return new TileDetails(new LinkedList<>(levels), worker!=null ? worker.getReadableCopy() : null);
    }
}
package it.polimi.ingsw.shared.model;

import it.polimi.ingsw.shared.color.Color;

import java.io.Serializable;

public class PlayerDetails implements Serializable {
    private final String username;
    private final CardDetails card;
    private final Color color;
    private final WorkerDetails[] workers;

    /**
     * creates a read only version
     * @param username to set
     * @param card to set
     * @param color to set
     * @param workers to set
     */
    public PlayerDetails(String username, CardDetails card, Color color, WorkerDetails[] workers) {
        this.username = username;
        this.card = card;
        this.color = color;
        this.workers = workers;
    }

    /**
     * getter
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * checks if it has a card
     * @return true if has a card
     */
    public boolean hasCard() {
        return card!=null;
    }

    /**
     * getter
     * @return the card if present
     */
    public CardDetails getCard() {
        return card;
    }

    /**
     * getter
     * @return the color if present
     */
    public Color getColor() {
        return color;
    }

    /**
     * checks if the player has a worker
     * @return true if has a color set
     */
    public boolean hasAColor() {
        return color!=null;
    }

    /**
     * compares the ids
     * @param worker to check
     * @return true if one have the same id
     */
    public boolean isHisWorker(WorkerDetails worker) {
        return worker.equals(workers[0]) || worker.equals(workers[1]);
    }
}

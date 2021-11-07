package main.java.server.model;

import main.java.shared.color.Color;
import main.java.shared.model.CardDetails;
import main.java.shared.model.PlayerDetails;
import main.java.shared.model.WorkerDetails;


/**
 * @author Gabriele Romano, Jasmine Perez, Jihane Samr
 */
public class Player {

    private final String username;
    private Card card;
    private Color color;
    private final Worker[] workers;

    /**
     * creates a player with only set the username
     * @param username to set
     */
    public Player(String username) {
        this.username = username;
        this.workers = new Worker[]{new Worker(), new Worker()};
    }

    /**
     * getter
     * @return this player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter
     * @return this player's card
     */
    public Card getCard() {
        return card;
    }

    /**
     * set this player's card
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * getter
     * @return this player's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * set this player's and workers' color
     * @param color to set
     */
    public void setColor(Color color) {
        this.color = color;
        for (Worker worker : workers)
            worker.setColor(color);
    }

    /**
     * checks if the worker passed is
     * @param worker to check
     * @return true if one of the two workers match
     */
    public boolean isHisWorker(Worker worker) {
        for(Worker w : this.workers){
            if(worker.equals(w)){
                return true;
            }
        }
        return false;
    }

    /**
     * getter
     * @return array of this player's workers
     */
    public Worker[] getWorkers() {
        return workers;
    }

    /**
     * creates and gets a read only copy to send to client
     * @return this player details
     */
    public PlayerDetails getReadableCopy() {
        return new PlayerDetails(username, card!=null ? new CardDetails(card.getCardName(), null) : null,
                color, new WorkerDetails[]{workers[0].getReadableCopy(),
                workers[1].getReadableCopy()});
    }

    /**
     * compares the usernames
     * @param p1 to check
     * @return true if they have the same username
     */
    public boolean equals(Player p1) {
        return username.equals(p1.username);
    }

}
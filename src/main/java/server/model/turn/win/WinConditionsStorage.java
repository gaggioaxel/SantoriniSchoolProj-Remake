package main.java.server.model.turn.win;

import com.google.gson.annotations.SerializedName;
import main.java.server.model.turn.win.limiter.WinLimiter;

import java.util.ArrayList;

public class WinConditionsStorage {

    @SerializedName("win_conditions")
    private ArrayList<WinCondition> winConditions;

    @SerializedName("applicable_win_limiters")
    private ArrayList<WinLimiter> winLimiter;


    /**
     * constructor that sets all the win conditions and if there are win limiters sets them
     * @param winConditions to set
     * @param winLimiter to set
     */
    public WinConditionsStorage(ArrayList<WinCondition> winConditions, WinLimiter winLimiter) {
        this.winConditions = winConditions;
        if (winLimiter != null) {
            this.winLimiter = new ArrayList<>(1);
            this.winLimiter.add(winLimiter);
        } else {
            this.winLimiter = null;
        }
    }

    /**
     * checks if has win limiter
     * @return true if win limiter is not null
     */
    public boolean hasWinLimiter() {
        return winLimiter!=null;
    }


    /**
     * retrieves and removes all win limiters
     * @return the win limiters field
     */
    public ArrayList<WinLimiter> removeWinLimiter() {
        ArrayList<WinLimiter> ret = winLimiter;
        this.winLimiter = null;
        return ret;
    }


    /**
     * retrieves and removes all win condition extensions
     * @return the win conditions field
     */
    public ArrayList<WinCondition> removeWinConditions() {
        ArrayList<WinCondition> ret = winConditions;
        winConditions = null;
        return ret;
    }

}

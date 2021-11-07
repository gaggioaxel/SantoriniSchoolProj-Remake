package main.java.server.model.turn.win;

import com.google.gson.annotations.SerializedName;
import main.java.server.model.turn.win.limiter.WinLimiter;

import java.util.ArrayList;

public class WinConditionsIncapsulator {

    @SerializedName("win_conditions")
    private ArrayList<WinCondition> winConditions;

    @SerializedName("applicable_win_limiter")
    private WinLimiter winLimiter;


    public WinConditionsIncapsulator(ArrayList<WinCondition> winConditions, WinLimiter winLimiter) {
        this.winConditions = winConditions;
        this.winLimiter = winLimiter;
    }

    public boolean hasWinLimiter() {
        return winLimiter!=null;
    }

    public WinLimiter removeWinLimiter() {
        WinLimiter ret = winLimiter;
        this.winLimiter = null;
        return ret;
    }

    public ArrayList<WinCondition> removeWinConditions() {
        ArrayList<WinCondition> ret = winConditions;
        winConditions = null;
        return ret;
    }

}

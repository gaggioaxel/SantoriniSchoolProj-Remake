package main.java.server.model.turn.building;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.server.model.turn.building.limiter.BuildingLimiter;
import main.java.shared.model.Action;

import java.util.ArrayList;

public interface Building {

    ArrayList<Action> getBuildActions(Player player, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters);

}

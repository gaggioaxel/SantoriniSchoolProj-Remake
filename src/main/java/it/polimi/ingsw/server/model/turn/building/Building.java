package it.polimi.ingsw.server.model.turn.building;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.building.limiter.BuildingLimiter;
import it.polimi.ingsw.shared.model.Action;

import java.util.ArrayList;

public interface Building {

    ArrayList<Action> getBuildActions(Player player, Board board, ActionsCache cache, ArrayList<BuildingLimiter> limiters);

}

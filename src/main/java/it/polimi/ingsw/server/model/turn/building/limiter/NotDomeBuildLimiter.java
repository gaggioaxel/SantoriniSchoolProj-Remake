package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;

//Hephaestus pt.2
public class NotDomeBuildLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> board.getTile(to).getIntLevel() == 3);
        return copy;
    }

}

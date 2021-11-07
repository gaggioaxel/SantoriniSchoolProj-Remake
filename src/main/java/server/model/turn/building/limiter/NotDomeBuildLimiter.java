package main.java.server.model.turn.building.limiter;

import main.java.server.model.Board;
import main.java.server.model.Player;
import main.java.server.model.action.ActionsCache;
import main.java.shared.utils.Point;

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

package it.polimi.ingsw.server.model.turn.building.limiter;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class WontMoveUpLimiter implements BuildingLimiter {

    @Override
    public ArrayList<Point> applyLimiter(Player player, Point from, ArrayList<Point> tos, Board board, ActionsCache cache) {
        ArrayList<Point> smOrLwLvBuild = sameOrLowerLevelBuildingNearby(from, tos, board);
        switch (smOrLwLvBuild.size()) {
            case 0:
                return new ArrayList<>(1);
            case 1:
                return filterThisPos(tos, smOrLwLvBuild.get(0));
            default:
                return tos;
        }
    }

    private ArrayList<Point> sameOrLowerLevelBuildingNearby(Point from, ArrayList<Point> tos, Board board) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> board.getDeltaLevel(to, from) > 0);
        return copy;
    }

    private ArrayList<Point> filterThisPos(ArrayList<Point> tos, Point pToFilter) {
        final AtomicInteger integer = new AtomicInteger();
        tos.forEach(to -> {
            if(to.equals(pToFilter))
                integer.set(tos.indexOf(to));
        });
        tos.remove(integer.get());
        return tos;
    }
}

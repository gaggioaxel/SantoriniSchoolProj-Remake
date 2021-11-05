package it.polimi.ingsw.server.model.turn.movement;

import it.polimi.ingsw.server.model.Board;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.shared.model.Action;
import it.polimi.ingsw.shared.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.shared.model.Action.ActionType;

public class SwapperMoveStrategy extends StandardMoveStrategy {


    /**
     * remove poses with my workers, >1 levels and dome
     * @param player passed down
     * @param board passed down
     * @param from passed down
     * @param tos passed down
     * @return tos filtered by my workers
     */
    @Override
    protected ArrayList<Point> applyFilters(Player player, Board board, Point from, ArrayList<Point> tos) {
        return applyMyWorkersFilter(player, board, board.applyMoreOneStepUpFilter(from, board.applyDomeFilter(tos)));

    }


    /**
     * creates forced moves by putting null in tos where there is no worker, and forced move where there is a worker (my workers filter applied yet)
     * @param player unused
     * @param board unused
     * @param from unused
     * @param tos to build a null forced move
     * @return map of <tos, forced moves to apply to other workers
     */
    @Override
    protected Map<Point, Action> getForcedMoves(Player player, Board board, Point from, ArrayList<Point> tos) {
        Map<Point, Action> forcedMovesMap = new HashMap<>(5);
        tos.forEach(to -> {
            if(board.getTile(to).hasWorker())
                forcedMovesMap.put(to, new Action(board.getTile(to).getWorker().getReadableCopy(), ActionType.MOVE, to, from, null, null));
            else
                forcedMovesMap.put(to, null);
        });
        return forcedMovesMap;
    }


    /**
     * removes all tos where there's a worker that belongs to player
     * @param player whose worker has to be checked
     * @param board that checks workers pos
     * @param tos to check
     * @return a copy of tos with player's workers
     */
    private ArrayList<Point> applyMyWorkersFilter(Player player, Board board, ArrayList<Point> tos) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> board.getTile(to).hasWorker() && player.isHisWorker(board.getTile(to).getWorker()));
        return copy;
    }

}
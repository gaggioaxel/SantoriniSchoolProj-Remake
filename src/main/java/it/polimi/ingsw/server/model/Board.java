package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Tile;
import it.polimi.ingsw.server.model.Worker;
import it.polimi.ingsw.server.model.action.ActionsCache;
import it.polimi.ingsw.server.model.turn.movement.limiter.MovementLimiter;
import it.polimi.ingsw.shared.model.BoardDetails;
import it.polimi.ingsw.shared.model.TileDetails;
import it.polimi.ingsw.shared.utils.Point;
import it.polimi.ingsw.shared.utils.Tuple;

import java.util.ArrayList;


public class Board {

    private final static int DIM = 5;
    private final Tile[][] tilesMatrix= new Tile[DIM][DIM];


    /**
     * creates a matrix of tiles
     */
    public Board() {
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++)
                tilesMatrix[i][j] = new Tile();
    }


    /**
     * @return every unoccupied point for workers placement
     */
    public ArrayList<Point> getFreePosesForWorkersPlacement() {
        ArrayList<Point> poses = new ArrayList<>(24);
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++)
                if(!tilesMatrix[i][j].hasWorker())
                    poses.add(new Point(j, i));
        return poses;
    }


    /**
     * checks all the poses around a specific point within the radius
     * @param pos center of the circle
     * @param radius to check positions
     * @return list of all the point around that are not out of board DIM bounds
     */
    public ArrayList<Point> getNearPoses(Point pos, int radius) {
        ArrayList<Point> nearPos = new ArrayList<>(8+(16*(radius-1)));
        for (int dy = -radius; dy < radius+1; dy++) {
            for (int dx = -radius; dx < radius+1; dx++) {
                if(!isOutOfBounds(pos.getX()+dx, pos.getY()+dy) & !pos.equals(pos.getX()+dx, pos.getY()+dy)) {
                    nearPos.add(new Point(pos.getX()+dx, pos.getY()+dy));
                }
            }
        }
        return nearPos;
    }


    /**
     * checks if a coordinate is out of the map
     * @param x coordinate
     * @param y coordinate
     * @return true if is out of bounds
     */
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 | x > DIM-1 | y < 0 | y > DIM-1;
    }


    /**
     * @param from position
     * @param to position
     * @return null if next pos of vector (from ; to) is out of bounds,
     * next pos otherwise
     */
    public Point getNextLinedUpPos(Point from, Point to) {
        int x = 2*to.getX() - from.getX();
        int y = 2*to.getY() - from.getY();
        return isOutOfBounds(new Point(x, y)) ? null : new Point(x, y);
    }


    /**
     * gets the level variation between two tiles (level of TO - level of FROM)
     * @param to pos of tile
     * @param from pos of tile
     * @return the value of the difference (also negative)
     */
    public int getDeltaLevel(Point to, Point from) {
        return tilesMatrix[to.getY()][to.getX()].getIntLevel() -
                tilesMatrix[from.getY()][from.getX()].getIntLevel();
    }


    /**
     * removes all the points whose tile has a dome
     * @param poses to check
     * @return a copy of poses without those whose tiles have a dome
     */
    public ArrayList<Point> applyDomeFilter(ArrayList<Point> poses) {
        ArrayList<Point> copy = new ArrayList<>(poses);
        copy.removeIf(pos -> tilesMatrix[pos.getY()][pos.getX()].hasDome());
        return copy;
    }


    /**
     * removes all the points whose tile has a worker
     * @param poses to check
     * @return a copy of poses without those whose tiles have a worker
     */
    public ArrayList<Point> applyWorkersFilter(ArrayList<Point> poses) {
        ArrayList<Point> copy = new ArrayList<>(poses);
        copy.removeIf(pos -> tilesMatrix[pos.getY()][pos.getX()].hasWorker());
        return copy;
    }


    /**
     * removes all the points whose tile has a difference level greater than 1 than the tile in from position
     * @param from point whose tile has to be compared
     * @param tos points to compare to from
     * @return a copy of tos without those that represent a tile with a building level greater than 1
     */
    public ArrayList<Point> applyMoreOneStepUpFilter(Point from, ArrayList<Point> tos) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.removeIf(to -> getDeltaLevel(to, from) > 1);
        return copy;
    }


    /**
     * removes all the points that are on the perimeter
     * @param poses to check
     * @return a copy of poses that are on the perimeter
     */
    public ArrayList<Point> applyPerimeterFilter(ArrayList<Point> poses) {
        ArrayList<Point> copy = new ArrayList<>(poses);
        copy.removeIf(this::isPerimeter);
        return copy;
    }


    /**
     * gets all the linked perimeter points if from is a perimeter point clockwise and counterclockwise
     * @param player acting
     * @param from pos of worker
     * @param cache to check pos
     * @param limiters to limit poses
     * @return all points linked (with duplicates)
     */
    public ArrayList<Point> getLinkedPerimeterAndNear(Player player, Point from, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        ArrayList<Point> poses = new ArrayList<>(144); //   [side -> (DIM-2) * 5(near poses) * 4(sides)     +   corners -> 4(corners) * 3(near poses) ] * 2 (clockwise and counterclockwise)
        if(!isPerimeter(from))
            return poses;
        poses.addAll(getLinkedPoses(player, from, true, cache, limiters));
        poses.addAll(getLinkedPoses(player, from, false, cache, limiters));

        return poses;
    }


    /**
     * walks through all linked poses from a point in clockwise or counterclockwise back to from or until a pos is limited by limiter
     * @param player that acts
     * @param from starting pos
     * @param clockwise true if has to walk in clockwise, false for counterclockwise
     * @param cache for limitations
     * @param limiters that limits
     * @return all linked poses and nearby
     */
    private ArrayList<Point> getLinkedPoses(Player player, Point from, boolean clockwise, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        Point fromPerimPoint = from;

        ArrayList<Point> poses = new ArrayList<>(72);
        Tuple<Point, Boolean> next;
        do {
            ArrayList<Point> tos = getNearPoses(fromPerimPoint, 1);
            //apply standard filters
            tos = applyDomeFilter(applyWorkersFilter(applyMoreOneStepUpFilter(fromPerimPoint, tos)));
            //apply extra filters
            for(MovementLimiter limiter : limiters)
                tos = limiter.applyLimiter(player, fromPerimPoint, tos, this, cache);
            poses.addAll(tos);

            next = checkAndGetNextPeriPoint(player, fromPerimPoint, clockwise, cache, limiters);
            //if (next pos is not ok AND is not a corner) OR (is a corner and the next of next pos is not ok)
            if((!next.y && !isCorner(next.x)) || (isCorner(next.x) && !checkAndGetNextPeriPoint(player, next.x, clockwise, cache, limiters).y))
                return poses;
            else
                fromPerimPoint = next.x;
        } while (!next.x.equals(from));

        return poses;
    }


    /**
     * get the next pos linked and if it's a valid pos
     * @param player acting
     * @param from position starting
     * @param clockwise if it's clockwise or counterclockwise
     * @param cache to check limiters
     * @param limiters to limit a pos
     * @return the next pos and if it's a valid pos
     */
    private Tuple<Point, Boolean> checkAndGetNextPeriPoint(Player player, Point from, boolean clockwise, ActionsCache cache, ArrayList<MovementLimiter> limiters) {
        Point to = getNextPerimeterPos(from, clockwise);
        ArrayList<Point> target = new ArrayList<>(1);
        target.add(to);
        for(MovementLimiter limiter : limiters)
            target = limiter.applyLimiter(player, from, target, this, cache);
        if(target.isEmpty() || tilesMatrix[to.getY()][to.getX()].hasDome() || tilesMatrix[to.getY()][to.getX()].hasWorker() || getDeltaLevel(to, from) > 1)
            return new Tuple<>(to, false);
        return new Tuple<>(to, true);
    }


    /**
     * gets the next perimeter pos linked
     * @param p starting point
     * @param clockwise for rotation adder
     * @return the next perimeter linked pos ( ex, clockwise 0,0 -> 1,0)
     */
    private Point getNextPerimeterPos(Point p, boolean clockwise) {
        int rotationAdder = (clockwise ? 1 : -1);
        int nextX = -1;
        int nextY = -1;

        if (isUpperSide(p)) {
            if((clockwise && !isTopRightCorner(p)) || (!clockwise && !isTopLeftCorner(p))) {
                nextX = p.getX() + rotationAdder;
                nextY = p.getY();
            }
        }
        if (isRightSide(p)) {
            if((clockwise && !isDownRightCorner(p) || (!clockwise && !isTopRightCorner(p)))) {
                nextX = p.getX();
                nextY = p.getY() + rotationAdder;
            }
        }
        if (isLowerSide(p)) {
            if ((clockwise && !isDownLeftCorner(p)) || (!clockwise && !isDownRightCorner(p))) {
                nextX = p.getX() - rotationAdder;
                nextY = p.getY();
            }
        }
        if (isLeftSide(p)) {
            if ((clockwise && !isTopLeftCorner(p)) || (!clockwise && !isDownLeftCorner(p))) {
                nextX = p.getX();
                nextY = p.getY() - rotationAdder;
            }
        }
        return new Point(nextX, nextY);
    }


    /**
     * checks if point is upper side
     * @param p to check
     * @return true if is of the upper side
     */
    private boolean isUpperSide(Point p) {
        return p.getY() == 0;
    }


    /**
     * checks if point is lower side
     * @param p to check
     * @return true if is of the lower side
     */
    private boolean isLowerSide(Point p) {
        return p.getY() == DIM-1;
    }

    /**
     * checks if point is left side
     * @param p to check
     * @return true if is of the left side
     */
    private boolean isLeftSide(Point p) {
        return p.getX() == 0;
    }


    /**
     * checks if point is right side
     * @param p to check
     * @return true if is of the right side
     */
    private boolean isRightSide(Point p) {
        return p.getX() == DIM-1;
    }


    /**
     * checks if point is a corner
     * @param p to check
     * @return true if is int the corner
     */
    private boolean isCorner(Point p) {
        return isTopRightCorner(p) || isTopLeftCorner(p) || isDownRightCorner(p) || isDownLeftCorner(p);
    }


    /**
     * checks if point is down left corner
     * @param p to check
     * @return true if is the down left corner
     */
    private boolean isDownLeftCorner(Point p) {
        return p.getX() == 0 && p.getY() == DIM - 1;
    }


    /**
     * checks if point is down right corner
     * @param p to check
     * @return true if is the down right corner
     */
    private boolean isDownRightCorner(Point p) {
        return p.getX() == DIM - 1 && p.getY() == DIM - 1;
    }


    /**
     * checks if point is top left corner
     * @param p to check
     * @return true if is the top left corner
     */
    private boolean isTopLeftCorner(Point p) {
        return p.getX() == 0 && p.getY() == 0;
    }


    /**
     * checks if point is top right corner
     * @param p to check
     * @return true if is the top right corner
     */
    private boolean isTopRightCorner(Point p) {
        return p.getX() == DIM - 1 && p.getY() == 0;
    }


    /**
     * insert from pos in tos list
     * @param selfPos to insert
     * @param tos array where to insert
     * @return a copy of tos with self pos inside
     */
    public ArrayList<Point> insertUnderYourFeetPos(Point selfPos, ArrayList<Point> tos) {
        ArrayList<Point> copy = new ArrayList<>(tos);
        copy.add(selfPos);
        return copy;
    }


    /**
     * checks if it's on a border
     * @param pos to check
     * @return true if it's on a border
     */
    public boolean isPerimeter(Point pos) {
        return pos.getX() == 0 || pos.getY() == DIM-1 || pos.getY() == 0 || pos.getY() == DIM-1;
    }


    /**
     * checks if position is inside the borders of the map
     * @param pos to check
     * @return true if out of borders
     */
    public boolean isOutOfBounds(Point pos) {
        return pos.getX() < 0 || pos.getX() > DIM-1 || pos.getY() < 0 || pos.getY() > DIM-1;
    }


    /**
     * gets the tile in (pos.x, pos.y)
     * @param pos whose tile must be retrieved (must be a valid pos)
     * @return tile in pos of the arraylist
     */
    public Tile getTile(Point pos) {
        return tilesMatrix[pos.getY()][pos.getX()];
    }


    /**
     * for every tile checks if there's a worker and if it's the target worker
     * @param worker fo find
     * @return position of worker or NULL if not found
     */
    public Point getWorkerPos(Worker worker) {
        for(int row=0; row<DIM; row++)
            for(int col=0; col<DIM; col++)
                if(tilesMatrix[row][col].hasWorker() &&
                        tilesMatrix[row][col].getWorker().equals(worker))
                    return new Point(col, row);
        return null;
    }


    /**
     * creates a read only copy to send to client
     * @return a board details instance with every tile that has the same build level and workers
     */
    public BoardDetails getReadableCopy() {
        TileDetails[][] tilesCopy = new TileDetails[DIM][DIM];
        for (int row = 0; row < DIM; row++) {
            for (int col = 0; col < DIM; col++) {
                tilesCopy[row][col] = tilesMatrix[row][col].getReadableCopy();
            }
        }
        return new BoardDetails(tilesCopy);
    }
}
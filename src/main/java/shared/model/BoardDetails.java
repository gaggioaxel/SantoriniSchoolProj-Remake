package main.java.shared.model;

import java.io.Serializable;

public class BoardDetails implements Serializable {

    private final TileDetails[][] tilesMatrix;

    /**
     * creates a read only version of every tile
     * @param tilesMatrix matrix of tiles
     */
    public BoardDetails(TileDetails[][] tilesMatrix) {
        this.tilesMatrix = tilesMatrix;
    }

    /**
     * gets the tile at this pos
     * @param row of the tile
     * @param column of the tile
     * @return read only version of the tile
     */
    public TileDetails getTile(int row, int column) {
        return this.tilesMatrix[row][column];
    }

}

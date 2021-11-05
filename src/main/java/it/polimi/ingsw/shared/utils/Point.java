package it.polimi.ingsw.shared.utils;


import java.io.Serializable;


public class Point implements Serializable {

    private final Tuple<Integer, Integer> pos;

    public Point(int x, int y) {
        pos = new Tuple<>(x, y);
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }



    public boolean equals(Point p) {
        return  p.pos.equals(pos);
    }

    public boolean equals(int x, int y) {
        return pos.x == x && pos.y == y;
    }

}
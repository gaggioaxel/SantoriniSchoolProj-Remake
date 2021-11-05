package it.polimi.ingsw.shared.utils;

import java.io.Serializable;

public class Triplet<X, Y, Z> implements Serializable {

    public final X x;

    public final Y y;

    public final Z z;

    public Triplet(X x, Y y, Z z) {
        this.x = x;
                this.y = y;
                        this.z = z;
    }

    public boolean equals(Triplet<X, Y, Z> tr) {
        return x.equals(tr.x) && y.equals(tr.y) && z.equals(tr.z);
    }
}

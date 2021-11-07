package main.java.shared.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tuple<X, Y> implements Serializable {

    @SerializedName("first_value")
    public final X x;

    @SerializedName("second_value")
    public final Y y;


    public Tuple(X x, Y y) {
        this.x = x;
                this.y = y;
    }

    public boolean equals(Tuple<X, Y> tu) {
        return x.equals(tu.x) && y.equals(tu.y);
    }
}

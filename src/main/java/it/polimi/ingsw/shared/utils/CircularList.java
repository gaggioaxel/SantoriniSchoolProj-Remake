package it.polimi.ingsw.shared.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

//extended ArrayList because in this context elements are more accessed than shifted


public class CircularList<T> extends LinkedList<T> implements Cloneable, Serializable {

    public CircularList() {
        super();
    }

    public CircularList(Collection<T> t){
        super(t);
    }

    public T getFirst() {
        return super.peek();
    }

    public void goNext() {
        super.add(super.pop());
    }

    /**
     * @param index to be set first
     */
    public void setFirst(int index) {
        for (int i = 0; i < index; i++) super.add(super.pop());
    }

    /**
     * sets first the element
     * @param t element
     */
    public void setFirst(T t) {
        while (! super.getFirst().equals(t)) super.add(super.pop());
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}

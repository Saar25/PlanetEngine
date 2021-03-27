package org.saar.gui.style.property;

public class Coordinate {

    private int value;

    public void add(int value) {
        set(get() + value);
    }

    public void sub(int value) {
        set(get() - value);
    }

    public void set(Coordinate coordinate) {
        set(coordinate.get());
    }

    public int get() {
        return this.value;
    }

    public void set(int value) {
        this.value = value;
    }
}

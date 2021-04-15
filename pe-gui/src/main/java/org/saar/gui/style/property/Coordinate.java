package org.saar.gui.style.property;

import org.saar.gui.style.Style;

public class Coordinate {

    private final Style parent;
    private final Style container;

    private int value;

    public Coordinate(Style parent, Style container) {
        this.parent = parent;
        this.container = container;
    }

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

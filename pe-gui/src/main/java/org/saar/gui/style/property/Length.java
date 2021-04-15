package org.saar.gui.style.property;

import org.saar.gui.style.Style;

public class Length {

    private final Style container;

    public Length(Style container) {
        this.container = container;
    }

    private int value;

    public void add(int value) {
        set(get() + value);
    }

    public void sub(int value) {
        set(get() - value);
    }

    public void set(Length length) {
        set(length.get());
    }

    public int get() {
        return this.value;
    }

    public void set(int value) {
        this.value = value;
    }
}

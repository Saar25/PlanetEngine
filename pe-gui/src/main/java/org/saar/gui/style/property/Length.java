package org.saar.gui.style.property;

import org.saar.gui.style.IStyle;

public class Length {

    private final IStyle parent;
    private final IStyle container;

    private int value;

    public Length(IStyle parent, IStyle container) {
        this.parent = parent;
        this.container = container;
    }

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

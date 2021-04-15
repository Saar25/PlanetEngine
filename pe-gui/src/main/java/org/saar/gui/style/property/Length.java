package org.saar.gui.style.property;

public class Length {

    protected int value = 0;

    public void set(int value) {
        this.value = value;
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
}

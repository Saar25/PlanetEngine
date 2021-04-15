package org.saar.gui.style.property;

import org.saar.core.screen.MainScreen;
import org.saar.gui.style.IStyle;
import org.saar.gui.style.value.StyleLength;
import org.saar.gui.style.value.StyleLengths;

public abstract class Length {

    protected final IStyle parent;
    protected final IStyle container;

    protected StyleLength value = StyleLengths.inherit();

    private Length(IStyle parent, IStyle container) {
        this.parent = parent;
        this.container = container;
    }

    public void set(StyleLength value) {
        this.value = value;
    }

    public void add(int value) {
        set(get() + value);
    }

    public void sub(int value) {
        set(StyleLengths.sub(this.value, value));
    }

    public void set(int value) {
        set(StyleLengths.pixels(value));
    }

    public void set(Length coordinate) {
        set(coordinate.get());
    }

    public abstract int get();

    public static class Width extends Length {

        public Width(IStyle parent, IStyle container) {
            super(parent, container);
        }

        @Override
        public int get() {
            return this.value.compute(this.parent.getX(), this.parent.getWidth());
        }
    }

    public static class Height extends Length {

        public Height(IStyle parent, IStyle container) {
            super(parent, container);
        }

        @Override
        public int get() {
            return this.value.compute(this.parent.getY(), this.parent.getHeight());
        }
    }

    public static class WidthGenesis extends Length {

        public WidthGenesis() {
            super(null, null);
        }

        @Override
        public int get() {
            return MainScreen.getInstance().getWidth();
        }
    }

    public static class HeightGenesis extends Length {

        public HeightGenesis() {
            super(null, null);
        }

        @Override
        public int get() {
            return MainScreen.getInstance().getHeight();
        }
    }
}

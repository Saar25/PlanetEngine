package org.saar.gui.style.property;

import org.saar.gui.style.IStyle;
import org.saar.gui.style.value.StyleCoordinate;
import org.saar.gui.style.value.StyleCoordinates;

public abstract class Coordinate {

    protected final IStyle parent;
    protected final IStyle container;

    protected StyleCoordinate value = StyleCoordinates.inherit();

    private Coordinate(IStyle parent, IStyle container) {
        this.parent = parent;
        this.container = container;
    }

    public void set(StyleCoordinate value) {
        this.value = value;
    }

    public void add(int value) {
        set(get() + value);
    }

    public void sub(int value) {
        set(StyleCoordinates.sub(this.value, value));
    }

    public void set(int value) {
        set(StyleCoordinates.pixels(value));
    }

    public void set(Coordinate coordinate) {
        set(coordinate.get());
    }

    public abstract int get();

    public static class X extends Coordinate {

        public X(IStyle parent, IStyle container) {
            super(parent, container);
        }

        @Override
        public int get() {
            return this.value.compute(this.parent.getX(),
                    this.parent.getWidth(), this.container.getWidth());
        }
    }

    public static class Y extends Coordinate {

        public Y(IStyle parent, IStyle container) {
            super(parent, container);
        }

        @Override
        public int get() {
            return this.value.compute(this.parent.getY(),
                    this.parent.getHeight(), this.container.getHeight());
        }
    }

    public static class XGenesis extends Coordinate {

        public XGenesis() {
            super(null, null);
        }

        @Override
        public int get() {
            return 0;
        }
    }

    public static class YGenesis extends Coordinate {

        public YGenesis() {
            super(null, null);
        }

        @Override
        public int get() {
            return 0;
        }
    }
}

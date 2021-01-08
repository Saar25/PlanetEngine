package org.saar.gui.style.property;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.saar.gui.style.StyleProperty;

public class Borders implements StyleProperty {

    public final Length top = new Length();
    public final Length right = new Length();
    public final Length bottom = new Length();
    public final Length left = new Length();

    private final Vector4i vector = new Vector4i();

    public Borders() {

    }

    public Borders(int all) {
        set(all);
    }

    public Borders(int top, int right, int bottom, int left) {
        set(top, right, bottom, left);
    }

    public void set(int top, int right, int bottom, int left) {
        this.top.set(top);
        this.right.set(right);
        this.bottom.set(bottom);
        this.left.set(left);
    }

    public void set(int all) {
        this.set(all, all, all, all);
    }

    public void set(Borders borders) {
        this.top.set(borders.top);
        this.right.set(borders.right);
        this.bottom.set(borders.bottom);
        this.left.set(borders.left);
    }

    public Vector4ic asVector4i() {
        return this.vector.set(
                this.top.get(),
                this.right.get(),
                this.bottom.get(),
                this.left.get());
    }

    @Override
    public String toString() {
        return String.format("[Borders: top=%d, right=%d, bottom=%d, left=%d]",
                this.top.get(), this.right.get(), this.bottom.get(), this.left.get());
    }
}

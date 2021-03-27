package org.saar.gui.style.property;

import org.joml.Vector2i;

public class Dimensions {

    public final Length width;
    public final Length height;

    private final Vector2i vector = new Vector2i();

    public Dimensions(Length width, Length height) {
        this.width = width;
        this.height = height;
    }

    public void set(Dimensions dimensions) {
        this.width.set(dimensions.width);
        this.height.set(dimensions.height);
    }

    public Vector2i asVector2i() {
        final int w = this.width.get();
        final int h = this.height.get();
        return this.vector.set(w, h);
    }
}

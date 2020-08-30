package org.saar.lwjgl.opengl.drawable;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class DrawableArraysInstanced implements GlDrawable {

    private final RenderMode mode;
    private final int first;
    private final int count;
    private final int instances;

    public DrawableArraysInstanced(RenderMode mode, int first, int count, int instances) {
        this.mode = mode;
        this.first = first;
        this.count = count;
        this.instances = instances;
    }

    @Override
    public void draw() {
        GlRendering.drawArraysInstanced(this.mode, this.first, this.count, this.instances);
    }
}

package org.saar.lwjgl.opengl.drawable;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class DrawableArrays implements GlDrawable {

    private final RenderMode mode;
    private final int first;
    private final int count;

    public DrawableArrays(RenderMode mode, int first, int count) {
        this.mode = mode;
        this.first = first;
        this.count = count;
    }

    @Override
    public void draw() {
        GlRendering.drawArrays(this.mode, this.first, this.count);
    }
}

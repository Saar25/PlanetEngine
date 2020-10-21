package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ArraysDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int count;

    public ArraysDrawCall(RenderMode mode, int count) {
        this.mode = mode;
        this.count = count;
    }

    public static void drawCall(RenderMode mode, int first, int count) {
        GlRendering.drawArrays(mode, first, count);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, 0, this.count);
    }
}

package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ArraysDrawCall implements DrawCall {

    private final RenderMode renderMode;
    private final int first;
    private final int count;

    public ArraysDrawCall(RenderMode renderMode, int first, int count) {
        this.renderMode = renderMode;
        this.first = first;
        this.count = count;
    }

    public ArraysDrawCall(RenderMode renderMode, int count) {
        this(renderMode, 0, count);
    }

    public static void drawCall(RenderMode mode, int first, int count) {
        GlRendering.drawArrays(mode, first, count);
    }

    public RenderMode getRenderMode() {
        return this.renderMode;
    }

    public int getFirst() {
        return this.first;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public void doDrawCall() {
        drawCall(this.renderMode, this.first, this.count);
    }
}

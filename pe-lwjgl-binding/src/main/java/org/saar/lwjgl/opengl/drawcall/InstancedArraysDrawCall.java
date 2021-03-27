package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int first;
    private final int count;
    private final int instances;

    public InstancedArraysDrawCall(RenderMode mode, int first, int count, int instances) {
        this.mode = mode;
        this.first = first;
        this.count = count;
        this.instances = instances;
    }

    public InstancedArraysDrawCall(RenderMode mode, int count, int instances) {
        this(mode, 0, count, instances);
    }

    public static void drawCall(RenderMode mode, int first, int count, int instances) {
        GlRendering.drawArraysInstanced(mode, first, count, instances);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, this.first, this.count, this.instances);
    }
}

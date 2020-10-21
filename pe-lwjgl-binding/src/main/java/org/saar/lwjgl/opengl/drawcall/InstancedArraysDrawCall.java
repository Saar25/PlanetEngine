package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int count;
    private final int instances;

    public InstancedArraysDrawCall(RenderMode mode, int count, int instances) {
        this.mode = mode;
        this.count = count;
        this.instances = instances;
    }

    public static void drawCall(RenderMode mode, int first, int count, int instances) {
        GlRendering.drawArraysInstanced(mode, first, count, instances);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, 0, this.count, this.instances);
    }
}

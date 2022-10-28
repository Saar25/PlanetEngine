package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysDrawCall implements DrawCall {

    private final RenderMode renderMode;
    private final int first;
    private final int count;
    private int instances;

    public InstancedArraysDrawCall(RenderMode renderMode, int first, int count, int instances) {
        this.renderMode = renderMode;
        this.first = first;
        this.count = count;
        this.instances = instances;
    }

    public InstancedArraysDrawCall(RenderMode renderMode, int count, int instances) {
        this(renderMode, 0, count, instances);
    }

    public static void drawCall(RenderMode mode, int first, int count, int instances) {
        GlRendering.drawArraysInstanced(mode, first, count, instances);
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

    public int getInstances() {
        return this.instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    @Override
    public void doDrawCall() {
        drawCall(this.renderMode, this.first, this.count, this.instances);
    }
}

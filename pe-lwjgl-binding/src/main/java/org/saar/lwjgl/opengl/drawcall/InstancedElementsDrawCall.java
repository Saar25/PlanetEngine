package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedElementsDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int count;
    private final DataType indexType;
    private final int indices;
    private final int instances;

    public InstancedElementsDrawCall(RenderMode mode, int count, DataType indexType, int indices, int instances) {
        this.mode = mode;
        this.count = count;
        this.indexType = indexType;
        this.indices = indices;
        this.instances = instances;
    }

    public InstancedElementsDrawCall(RenderMode mode, int count, DataType indexType, int instances) {
        this(mode, count, indexType, 0, instances);
    }

    public static void drawCall(RenderMode mode, int count, DataType indexType, int indices, int instances) {
        GlRendering.drawElementsInstanced(mode, count, indexType, indices, instances);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, this.count, this.indexType, this.indices, this.instances);
    }
}
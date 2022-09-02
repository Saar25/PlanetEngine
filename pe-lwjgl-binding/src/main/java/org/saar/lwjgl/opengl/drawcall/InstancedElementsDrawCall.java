package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedElementsDrawCall implements DrawCall {

    private final RenderMode renderMode;
    private final int count;
    private final DataType type;
    private final long indices;
    private int instances;

    public InstancedElementsDrawCall(RenderMode renderMode, int count, DataType type, long indices, int instances) {
        this.renderMode = renderMode;
        this.count = count;
        this.type = type;
        this.indices = indices;
        this.instances = instances;
    }

    public InstancedElementsDrawCall(RenderMode renderMode, int count, DataType type, int instances) {
        this(renderMode, count, type, 0, instances);
    }

    public static void drawCall(RenderMode mode, int count, DataType indexType, long indices, int instances) {
        GlRendering.drawElementsInstanced(mode, count, indexType, indices, instances);
    }

    public RenderMode getRenderMode() {
        return this.renderMode;
    }

    public int getCount() {
        return this.count;
    }

    public DataType getType() {
        return this.type;
    }

    public long getIndices() {
        return this.indices;
    }

    public int getInstances() {
        return this.instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    @Override
    public void doDrawCall() {
        drawCall(this.renderMode, this.count, this.type, this.indices, this.instances);
    }
}

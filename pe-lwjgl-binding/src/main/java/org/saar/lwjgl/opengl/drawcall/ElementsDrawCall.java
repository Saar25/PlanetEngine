package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsDrawCall implements DrawCall {

    private final RenderMode renderMode;
    private final int count;
    private final DataType type;
    private final long indices;

    public ElementsDrawCall(RenderMode renderMode, int count, DataType type, long indices) {
        this.renderMode = renderMode;
        this.count = count;
        this.type = type;
        this.indices = indices;
    }

    public ElementsDrawCall(RenderMode renderMode, int count, DataType type) {
        this(renderMode, count, type, 0);
    }

    public static void drawCall(RenderMode mode, int count, DataType indexType, long indices) {
        GlRendering.drawElements(mode, count, indexType, indices);
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

    @Override
    public void doDrawCall() {
        drawCall(this.renderMode, this.count, this.type, this.indices);
    }
}

package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int count;
    private final DataType indexType;
    private final int indices;


    public ElementsDrawCall(RenderMode mode, int count, DataType indexType, int indices) {
        this.mode = mode;
        this.count = count;
        this.indexType = indexType;
        this.indices = indices;
    }

    public ElementsDrawCall(RenderMode mode, int count, DataType indexType) {
        this(mode, count, indexType, 0);
    }

    public static void drawCall(RenderMode mode, int count, DataType indexType, long indices) {
        GlRendering.drawElements(mode, count, indexType, indices);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, this.count, this.indexType, this.indices);
    }
}

package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsDrawCall implements DrawCall {

    private final RenderMode mode;
    private final int count;
    private final DataType indexType;

    public ElementsDrawCall(RenderMode renderMode, int count, DataType indexType) {
        this.mode = renderMode;
        this.count = count;
        this.indexType = indexType;
    }

    public static void drawCall(RenderMode mode, int count, DataType indexType, long indices) {
        GlRendering.drawElements(mode, count, indexType, indices);
    }

    @Override
    public void doDrawCall() {
        drawCall(this.mode, this.count, this.indexType, 0);
    }
}

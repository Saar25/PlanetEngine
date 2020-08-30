package org.saar.lwjgl.opengl.drawable;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class DrawableElements implements GlDrawable {

    private final RenderMode mode;
    private final int count;
    private final DataType dataType;
    private final long indices;

    public DrawableElements(RenderMode mode, int count, DataType dataType, long indices) {
        this.mode = mode;
        this.count = count;
        this.dataType = dataType;
        this.indices = indices;
    }

    @Override
    public void draw() {
        GlRendering.drawElements(this.mode, this.count, this.dataType, this.indices);
    }
}

package org.saar.lwjgl.opengl.drawable;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class DrawableElementsInstanced implements GlDrawable {

    private final RenderMode mode;
    private final int count;
    private final DataType dataType;
    private final long indices;
    private final int instances;

    public DrawableElementsInstanced(RenderMode mode, int count, DataType dataType, long indices, int instances) {
        this.mode = mode;
        this.count = count;
        this.dataType = dataType;
        this.indices = indices;
        this.instances = instances;
    }

    @Override
    public void draw() {
        GlRendering.drawElementsInstanced(this.mode, this.count, this.dataType, this.indices, this.instances);
    }
}

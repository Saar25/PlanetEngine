package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsMesh extends MeshBase implements Mesh {

    private final RenderMode mode;
    private final int count;
    private final DataType indexType;
    private final long indices;

    public ElementsMesh(IVao vao, RenderMode renderMode, int count, DataType indexType) {
        super(vao);
        this.mode = renderMode;
        this.count = count;
        this.indexType = indexType;
        this.indices = 0;
    }

    @Override
    protected void doDrawCall() {
        GlRendering.drawElements(this.mode, this.count, this.indexType, this.indices);
    }
}

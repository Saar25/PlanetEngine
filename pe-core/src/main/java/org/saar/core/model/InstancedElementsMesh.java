package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedElementsMesh extends MeshBase implements Mesh {

    private final RenderMode mode;
    private final int count;
    private final DataType indexType;
    private final int instances;

    public InstancedElementsMesh(IVao vao, RenderMode mode, int count, DataType indexType, int instances) {
        super(vao);
        this.mode = mode;
        this.count = count;
        this.indexType = indexType;
        this.instances = instances;
    }

    @Override
    protected void doDrawCall() {
        GlRendering.drawElementsInstanced(this.mode, this.count, this.indexType, 0, this.instances);
    }
}

package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysMesh extends MeshBase implements Mesh {

    private final RenderMode mode;
    private final int count;
    private final int instances;

    public InstancedArraysMesh(IVao vao, RenderMode mode, int count, int instances) {
        super(vao);
        this.mode = mode;
        this.count = count;
        this.instances = instances;
    }

    @Override
    protected void doDrawCall() {
        GlRendering.drawArraysInstanced(this.mode, 0, this.count, this.instances);
    }
}

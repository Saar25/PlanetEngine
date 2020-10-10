package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ArraysMesh extends MeshBase implements Mesh {

    private final RenderMode mode;
    private final int count;

    public ArraysMesh(IVao vao, RenderMode mode, int count) {
        super(vao);
        this.mode = mode;
        this.count = count;
    }

    @Override
    protected void doDrawCall() {
        GlRendering.drawArrays(this.mode, 0, this.count);
    }
}

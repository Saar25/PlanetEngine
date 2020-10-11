package org.saar.core.model;

import org.saar.lwjgl.opengl.objects.vaos.IVao;

public abstract class MeshBase implements Mesh {

    private final IVao vao;

    public MeshBase(IVao vao) {
        this.vao = vao;
    }

    @Override
    public final void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        doDrawCall();
    }

    @Override
    public final void delete() {
        this.vao.delete();
    }

    protected abstract void doDrawCall();
}

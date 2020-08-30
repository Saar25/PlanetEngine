package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysModel implements Model {

    private final IVao vao;

    private final RenderMode renderMode;
    private final int vertices;
    private final int instances;

    public InstancedArraysModel(IVao vao, RenderMode renderMode, int vertices, int instances) {
        this.vao = vao;
        this.vertices = vertices;
        this.renderMode = renderMode;
        this.instances = instances;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawArraysInstanced(renderMode,
                0, this.vertices, this.instances);
    }

    @Override
    public void delete() {
        this.vao.delete();
    }
}

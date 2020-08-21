package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedArraysModel implements Model {

    private final Vao vao;
    private final int vertices;
    private final int instances;
    private final RenderMode renderMode;

    public InstancedArraysModel(Vao vao, int vertices, int instances, RenderMode renderMode) {
        this.vao = vao;
        this.vertices = vertices;
        this.instances = instances;
        this.renderMode = renderMode;
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
        this.vao.delete(true);
    }
}

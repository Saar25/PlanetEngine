package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ArraysModel implements Model {

    private final Vao vao;
    private final int vertices;
    private final RenderMode renderMode;

    public ArraysModel(Vao vao, int vertices, RenderMode renderMode) {
        this.vao = vao;
        this.vertices = vertices;
        this.renderMode = renderMode;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawArrays(this.renderMode, 0, this.vertices);
    }

    @Override
    public void delete() {
        this.vao.delete(true);
    }
}

package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ArraysModel implements Model {

    private final IVao vao;

    private final RenderMode renderMode;
    private final int vertices;

    public ArraysModel(IVao vao, RenderMode renderMode, int vertices) {
        this.vao = vao;
        this.renderMode = renderMode;
        this.vertices = vertices;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawArrays(this.renderMode, 0, this.vertices);
    }

    @Override
    public void delete() {
        this.vao.delete();
    }
}

package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsModel implements Model {

    private final Vao vao;
    private final int indices;
    private final RenderMode renderMode;

    public ElementsModel(Vao vao, int indices, RenderMode renderMode) {
        this.vao = vao;
        this.indices = indices;
        this.renderMode = renderMode;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawElements(this.renderMode, this.indices, DataType.U_INT, 0);
    }

    @Override
    public void delete() {
        this.vao.delete(true);
    }
}

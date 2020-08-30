package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.vaos.IVao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsModel implements Model {

    private final IVao vao;

    private final RenderMode renderMode;
    private final int indices;
    private final DataType indexType;

    public ElementsModel(IVao vao, RenderMode renderMode, int indices, DataType indexType) {
        this.vao = vao;
        this.renderMode = renderMode;
        this.indices = indices;
        this.indexType = indexType;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawElements(this.renderMode,
                this.indices, this.indexType, 0);
    }

    @Override
    public void delete() {
        this.vao.delete();
    }
}

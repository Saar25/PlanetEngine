package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class InstancedElementsModel implements Model {

    private final Vao vao;

    private final RenderMode renderMode;
    private final int indices;
    private final DataType indicesType;
    private final int instances;

    public InstancedElementsModel(Vao vao, RenderMode renderMode, int indices,
                                  DataType indicesType, int instances) {
        this.vao = vao;
        this.renderMode = renderMode;
        this.indices = indices;
        this.indicesType = indicesType;
        this.instances = instances;
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawElementsInstanced(renderMode,
                indices, indicesType, 0, instances);
    }

    @Override
    public void delete() {

    }
}

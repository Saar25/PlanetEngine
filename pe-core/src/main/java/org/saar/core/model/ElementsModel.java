package org.saar.core.model;

import org.saar.core.model.data.IndexModelData;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

public class ElementsModel implements Model {

    private final Vao vao;
    private final int indices;
    private final RenderMode renderMode;

    public ElementsModel(RenderMode renderMode, IndexModelData indexData, ModelData... modelDataInfo) {
        this.vao = Vao.create();
        this.renderMode = renderMode;
        this.indices = indexData.indices();
        this.load(indexData, modelDataInfo);
    }

    private void load(IndexModelData indexData, ModelData... modelDataInfo) {
        int indexOffset = 0;
        this.vao.loadIndexBuffer(indexData.vbo());
        for (ModelData modelData : modelDataInfo) {
            final Attribute[] attributes = modelData.attributes(indexOffset);
            this.vao.loadDataBuffer(modelData.vbo(), attributes);
            indexOffset += attributes.length;
        }
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

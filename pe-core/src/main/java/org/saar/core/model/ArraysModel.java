package org.saar.core.model;

import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.GlRendering;

import java.util.Arrays;

public class ArraysModel implements Model {

    private final Vao vao;
    private final int count;
    private final RenderMode renderMode;

    public ArraysModel(RenderMode renderMode, ModelAttribute... modelAttributes) {
        this.vao = Vao.create();
        this.count = count(modelAttributes);
        this.renderMode = renderMode;

        for (ModelAttribute modelAttribute : modelAttributes) {
            this.vao.loadDataBuffer(modelAttribute.vbo(), modelAttribute.attributes());
        }
    }

    private static int count(ModelAttribute... attributes) {
        return Arrays.stream(attributes).mapToInt(ModelAttribute::count).sum();
    }

    @Override
    public void draw() {
        this.vao.bind();
        this.vao.enableAttributes();
        GlRendering.drawArrays(this.renderMode, 0, this.count);
    }

    @Override
    public void delete() {

    }
}

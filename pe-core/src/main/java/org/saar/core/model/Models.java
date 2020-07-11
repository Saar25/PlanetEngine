package org.saar.core.model;

import org.saar.core.model.vertex.Vertex;
import org.saar.core.model.vertex.VerticesBuffer;
import org.saar.lwjgl.opengl.constants.RenderMode;

import java.util.Arrays;

public final class Models {

    private Models() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static Model arraysModel(RenderMode renderMode, VerticesBuffer buffer, Vertex... vertices) {
        Arrays.stream(vertices).forEach(vertex -> vertex.write(buffer));
        return new ArraysModel(buffer.vao(), vertices.length, renderMode);
    }

    public static Model elementsModel(RenderMode renderMode, VerticesBuffer buffer,
                                      int[] indices, Vertex... vertices) {
        Arrays.stream(indices).forEach(buffer::writeIndex);
        Arrays.stream(vertices).forEach(vertex -> vertex.write(buffer));
        return new ElementsModel(buffer.vao(), indices.length, renderMode);
    }
}

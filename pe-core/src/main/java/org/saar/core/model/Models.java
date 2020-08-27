package org.saar.core.model;

import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;

public final class Models {

    private Models() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static <T extends Vertex> Model arraysModel(
            RenderMode renderMode, ModelBufferWriter<T> writer, ModelVertices<T> vertices) {
        writer.write(vertices);
        return new ArraysModel(writer.toVao(), renderMode, vertices.count());
    }

    public static <T extends Vertex> Model elementsModel(
            RenderMode renderMode, ModelBufferWriter<T> writer,
            ModelIndices indices, ModelVertices<T> vertices) {
        writer.write(vertices);
        writer.write(indices);
        return new ElementsModel(writer.toVao(), renderMode, indices.count(), DataType.U_INT);
    }
}

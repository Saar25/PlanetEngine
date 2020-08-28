package org.saar.core.model;

import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;

import java.util.List;

public final class Models {

    private Models() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static <T extends Vertex> Model arraysModel(
            RenderMode renderMode, ModelBufferWriter<T> writer, List<T> vertices) {
        writer.write(vertices);
        return new ArraysModel(writer.toVao(), renderMode, vertices.size());
    }

    public static <T extends Vertex> Model elementsModel(
            RenderMode renderMode, ModelBufferWriter<T> writer,
            ModelIndices indices, List<T> vertices) {
        writer.write(vertices);
        writer.write(indices);
        return new ElementsModel(writer.toVao(), renderMode, indices.count(), DataType.U_INT);
    }
}

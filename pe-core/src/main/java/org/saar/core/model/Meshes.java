package org.saar.core.model;

import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;

import java.util.List;

public final class Meshes {

    private Meshes() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static <T extends Vertex> Mesh arraysModel(
            RenderMode renderMode, ModelBufferWriter<T> writer, List<T> vertices) {
        writer.write(vertices);
        return new ArraysMesh(writer.toVao(), renderMode, vertices.size());
    }

    public static <T extends Vertex> Mesh elementsModel(
            RenderMode renderMode, ModelBufferWriter<T> writer,
            ModelIndices indices, List<T> vertices) {
        writer.write(vertices);
        writer.write(indices);
        return new ElementsMesh(writer.toVao(), renderMode, indices.count(), DataType.U_INT);
    }
}

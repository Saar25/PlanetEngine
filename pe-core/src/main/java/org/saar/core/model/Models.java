package org.saar.core.model;

import org.saar.core.model.vertex.ModelBuffer;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.lwjgl.opengl.constants.RenderMode;

public final class Models {

    private Models() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static Model arraysModel(RenderMode renderMode, ModelBuffer buffer, ModelVertices<?> vertices) {
        vertices.write(buffer);
        return new ArraysModel(buffer.vao(), vertices.count(), renderMode);
    }

    public static Model elementsModel(RenderMode renderMode, ModelBuffer buffer,
                                      ModelIndices indices, ModelVertices<?> vertices) {
        indices.write(buffer);
        vertices.write(buffer);
        return new ElementsModel(buffer.vao(), indices.count(), renderMode);
    }
}

package org.saar.core.model.vertex;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.Vao;

public interface ModelBufferWriter<T extends Vertex> {

    void write(ModelVertices<T> vertices);

    void write(ModelIndices indices);

    Vao toVao();

}

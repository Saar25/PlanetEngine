package org.saar.core.model.vertex;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.Vao;

import java.util.List;

public interface ModelBufferWriter<T extends Vertex> {

    void write(List<T> vertices);

    void write(ModelIndices indices);

    Vao toVao();

}

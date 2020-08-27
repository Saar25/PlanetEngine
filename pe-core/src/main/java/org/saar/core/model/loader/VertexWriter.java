package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public interface VertexWriter<T extends Vertex> {

    void writeVertex(T vertex, BufferWriter writer);

    Attribute[] vertexAttributes();

    int vertexBytes();

}

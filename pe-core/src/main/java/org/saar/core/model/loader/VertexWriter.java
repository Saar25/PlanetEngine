package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public interface VertexWriter<T extends Vertex> {

    GlPrimitive[] vertexValues(T vertex);

    Attribute[] vertexAttributes();

    int vertexBytes();

}

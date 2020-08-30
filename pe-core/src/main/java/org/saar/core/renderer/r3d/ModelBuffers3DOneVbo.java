package org.saar.core.renderer.r3d;

import org.saar.core.model.loader.ModelBuffersSingleVbo;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.maths.objects.Transform;

public class ModelBuffers3DOneVbo extends ModelBuffersSingleVbo<Node3D, Vertex3D> implements ModelBuffers3D {

    private static final Attribute[] vertexAttributes = {positionAttribute, coloursAttribute};

    public ModelBuffers3DOneVbo(int vertices, int indices, int instances) {
        super(vertices, indices, instances, vertexAttributes, transformAttributes);
    }

    @Override
    public void writeInstance(Node3D instance) {
        final Transform transform = instance.getTransform();
        instanceWriter().write(transform.getTransformationMatrix());
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        vertexWriter().write(vertex.getPosition3f());
        vertexWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        indexWriter().write(index);
    }
}

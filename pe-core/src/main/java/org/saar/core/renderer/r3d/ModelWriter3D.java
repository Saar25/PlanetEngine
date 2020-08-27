package org.saar.core.renderer.r3d;

import org.saar.core.model.loader.NodeWriter;
import org.saar.core.model.loader.VertexWriter;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.maths.objects.Transform;

public class ModelWriter3D implements VertexWriter<Vertex3D>, NodeWriter<Node3D> {

    private static final int vertexBytes = 6;
    private static final Attribute[] attributes = new Attribute[]{
            Attribute.of(0, 3, DataType.FLOAT, true),
            Attribute.of(1, 3, DataType.FLOAT, true),
    };

    private static final int instanceBytes = 6;
    private static final Attribute[] instanceAttributes = new Attribute[]{
            Attribute.ofInstance(2, 4, DataType.FLOAT, false),
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
    };

    @Override
    public void writeInstance(Node3D instance, BufferWriter writer) {
        final Transform transform = instance.getTransform();
        writer.write(transform.getTransformationMatrix());
    }

    @Override
    public void writeVertex(Vertex3D vertex, BufferWriter writer) {
        writer.write(vertex.getPosition3f());
        writer.write(vertex.getColour3f());
    }

    @Override
    public Attribute[] instanceAttributes() {
        return ModelWriter3D.attributes;
    }

    @Override
    public int instanceBytes() {
        return ModelWriter3D.instanceBytes;
    }

    @Override
    public Attribute[] vertexAttributes() {
        return ModelWriter3D.instanceAttributes;
    }

    @Override
    public int vertexBytes() {
        return ModelWriter3D.vertexBytes;
    }
}

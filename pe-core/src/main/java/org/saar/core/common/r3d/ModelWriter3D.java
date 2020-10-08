package org.saar.core.common.r3d;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelNodeWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.maths.transform.Transform;

public abstract class ModelWriter3D implements ModelNodeWriter<Node3D>, ModelVertexWriter<Vertex3D>, ModelIndexWriter {

    public abstract BufferWriter getInstanceWriter();

    public abstract BufferWriter getPositionWriter();

    public abstract BufferWriter getNormalWriter();

    public abstract BufferWriter getColourWriter();

    public abstract BufferWriter getIndexWriter();

    @Override
    public void writeNode(Node3D instance) {
        final Transform transform = instance.getTransform();
        getInstanceWriter().write(transform.getTransformationMatrix());
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        getPositionWriter().write(vertex.getPosition3f());
        getNormalWriter().write(vertex.getNormal3f());
        getColourWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        getIndexWriter().write(index);
    }

}

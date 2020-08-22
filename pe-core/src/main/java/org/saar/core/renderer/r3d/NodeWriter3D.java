package org.saar.core.renderer.r3d;

import org.saar.core.model.data.DataWriter;
import org.saar.maths.objects.Transform;

import java.util.Collections;
import java.util.List;

public class NodeWriter3D {

    private final DataWriter dataWriter = new DataWriter();
    private final DataWriter instanceWriter = new DataWriter();
    private final DataWriter indexWriter = new DataWriter();

    private int vertices = 0;
    private int indexOffset = 0;

    private void writeVertex(Vertex3D vertex) {
        getDataWriter().write(vertex.getPosition3f());
        getDataWriter().write(vertex.getColour3f());
        this.vertices++;
    }

    private void writeIndex(int index) {
        getIndexWriter().write(this.indexOffset + index);
    }

    private void writeInstance(InstanceNode3D instance) {
        final Transform transform = instance.getTransform();
        this.instanceWriter.write(transform.getTransformationMatrix());
    }

    public void write(Node3D node) {
        this.write(node, Collections.singletonList(node));
    }

    public void write(ModelNode3D model, List<? extends InstanceNode3D> nodes) {
        model.getVertices().getVertices().forEach(this::writeVertex);
        model.getIndices().getIndices().forEach(this::writeIndex);
        nodes.forEach(this::writeInstance);
        this.indexOffset = this.vertices;
    }

    public void write(List<Node3D> nodes) {
        nodes.forEach(this::write);
    }

    public DataWriter getDataWriter() {
        return this.dataWriter;
    }

    public DataWriter getInstanceWriter() {
        return this.instanceWriter;
    }

    public DataWriter getIndexWriter() {
        return this.indexWriter;
    }
}

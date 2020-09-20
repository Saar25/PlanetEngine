package org.saar.core.common.r3d;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelNodeWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.maths.objects.Transform;

public class ModelWriter3D implements ModelNodeWriter<Node3D>, ModelVertexWriter<Vertex3D>, ModelIndexWriter {

    private final BufferWriter instanceWriter;
    private final BufferWriter positionWriter;
    private final BufferWriter normalWriter;
    private final BufferWriter colourWriter;
    private final BufferWriter indexWriter;

    public ModelWriter3D(BufferWriter instanceWriter, BufferWriter positionWriter,
                         BufferWriter normalWriter, BufferWriter colourWriter, BufferWriter indexWriter) {
        this.instanceWriter = instanceWriter;
        this.positionWriter = positionWriter;
        this.normalWriter = normalWriter;
        this.colourWriter = colourWriter;
        this.indexWriter = indexWriter;
    }

    @Override
    public void writeNode(Node3D instance) {
        final Transform transform = instance.getTransform();
        this.instanceWriter.write(transform.getTransformationMatrix());
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        this.positionWriter.write(vertex.getPosition3f());
        this.normalWriter.write(vertex.getNormal3f());
        this.colourWriter.write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexWriter.write(index);
    }

}

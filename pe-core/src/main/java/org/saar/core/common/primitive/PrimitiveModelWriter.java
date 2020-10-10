package org.saar.core.common.primitive;

import org.saar.core.model.mesh.writers.MeshIndexWriter;
import org.saar.core.model.mesh.writers.MeshNodeWriter;
import org.saar.core.model.mesh.writers.MeshVertexWriter;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class PrimitiveModelWriter implements MeshNodeWriter<PrimitiveNode>, MeshVertexWriter<PrimitiveVertex>, MeshIndexWriter {

    private final BufferWriter instanceWriter;
    private final BufferWriter vertexWriter;
    private final BufferWriter indexWriter;

    public PrimitiveModelWriter(BufferWriter instanceWriter,
                                BufferWriter vertexWriter,
                                BufferWriter indexWriter) {
        this.instanceWriter = instanceWriter;
        this.vertexWriter = vertexWriter;
        this.indexWriter = indexWriter;
    }

    @Override
    public void writeNode(PrimitiveNode node) {
        for (GlPrimitive value : node.getValues()) {
            value.write(this.instanceWriter);
        }
    }

    @Override
    public void writeVertex(PrimitiveVertex vertex) {
        for (GlPrimitive value : vertex.getValues()) {
            value.write(this.vertexWriter);
        }
    }

    @Override
    public void writeIndex(int index) {
        this.indexWriter.write(index);
    }
}

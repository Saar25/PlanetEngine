package org.saar.core.common.primitive;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelNodeWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class PrimitiveModelWriter implements ModelNodeWriter<PrimitiveNode>, ModelVertexWriter<PrimitiveVertex>, ModelIndexWriter {

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

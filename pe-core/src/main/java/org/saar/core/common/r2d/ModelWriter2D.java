package org.saar.core.common.r2d;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class ModelWriter2D implements ModelVertexWriter<Vertex2D>, ModelIndexWriter {

    private final BufferWriter positionWriter;
    private final BufferWriter colourWriter;
    private final BufferWriter indexWriter;

    public ModelWriter2D(BufferWriter positionWriter, BufferWriter colourWriter, BufferWriter indexWriter) {
        this.positionWriter = positionWriter;
        this.colourWriter = colourWriter;
        this.indexWriter = indexWriter;
    }

    @Override
    public void writeVertex(Vertex2D vertex) {
        this.positionWriter.write(vertex.getPosition2f());
        this.colourWriter.write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexWriter.write(index);
    }

}

package org.saar.core.common.obj;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class ObjModelWriter implements ModelVertexWriter<IObjVertex>, ModelIndexWriter {

    private final BufferWriter positionWriter;
    private final BufferWriter uvCoordWriter;
    private final BufferWriter normalWriter;
    private final BufferWriter indexWriter;

    public ObjModelWriter(BufferWriter positionWriter, BufferWriter uvCoordWriter,
                          BufferWriter normalWriter, BufferWriter indexWriter) {
        this.positionWriter = positionWriter;
        this.uvCoordWriter = uvCoordWriter;
        this.normalWriter = normalWriter;
        this.indexWriter = indexWriter;
    }

    @Override
    public void writeVertex(IObjVertex vertex) {
        this.positionWriter.write(vertex.getPosition3f());
        this.uvCoordWriter.write(vertex.getUvCoord2f());
        this.normalWriter.write(vertex.getNormal3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexWriter.write(index);
    }

}

package org.saar.core.common.normalmap;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class NormalMappedModelWriter implements ModelVertexWriter<NormalMappedVertex>, ModelIndexWriter {

    public abstract BufferWriter getPositionWriter();

    public abstract BufferWriter getUvCoordWriter();

    public abstract BufferWriter getNormalWriter();

    public abstract BufferWriter getTangentWriter();

    public abstract BufferWriter getBiTangentWriter();

    public abstract BufferWriter getIndexWriter();

    @Override
    public void writeVertex(NormalMappedVertex vertex) {
        getPositionWriter().write(vertex.getPosition3f());
        getUvCoordWriter().write(vertex.getUvCoord2f());
        getNormalWriter().write(vertex.getNormal3f());
        getTangentWriter().write(vertex.getTangent3f());
        getBiTangentWriter().write(vertex.getBiTangent3f());
    }

    @Override
    public void writeIndex(int index) {
        getIndexWriter().write(index);
    }

}

package org.saar.core.common.flatreflected;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class FlatReflectedModelWriter implements ModelVertexWriter<FlatReflectedVertex>, ModelIndexWriter {

    public abstract BufferWriter getPositionWriter();

    public abstract BufferWriter getNormalBuffer();

    public abstract BufferWriter getIndexBuffer();

    @Override
    public void writeIndex(int index) {
        getIndexBuffer().write(index);
    }

    @Override
    public void writeVertex(FlatReflectedVertex vertex) {
        getPositionWriter().write(vertex.getPosition3f());
        getNormalBuffer().write(vertex.getNormal3f());
    }

}

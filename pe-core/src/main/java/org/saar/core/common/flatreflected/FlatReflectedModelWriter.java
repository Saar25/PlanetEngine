package org.saar.core.common.flatreflected;

import org.saar.core.model.loader.ModelVertexWriter;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class FlatReflectedModelWriter implements ModelVertexWriter<FlatReflectedVertex> {

    public abstract BufferWriter getPositionWriter();

    public abstract BufferWriter getNormalBuffer();

    @Override
    public void writeVertex(FlatReflectedVertex vertex) {
        getPositionWriter().write(vertex.getPosition3f());
        getNormalBuffer().write(vertex.getNormal3f());
    }

}

package org.saar.core.renderer.basic;

import org.saar.core.model.vertex.*;
import org.saar.lwjgl.opengl.constants.DataType;

public class BasicBufferWriter extends AbstractModelBufferWriter<BasicVertex> implements ModelBufferWriter<BasicVertex> {

    private final ModelBuffer buffer = new ModelBufferSingleVbo(
            new ModelAttribute(2, true, DataType.FLOAT),
            new ModelAttribute(3, true, DataType.FLOAT));

    @Override
    protected void writeVertex(BasicVertex vertex) {
        this.buffer.write(vertex.getPosition2f().x());
        this.buffer.write(vertex.getPosition2f().y());
        this.buffer.write(vertex.getColour3f().x());
        this.buffer.write(vertex.getColour3f().y());
        this.buffer.write(vertex.getColour3f().z());
    }

    @Override
    protected void writeIndex(int index) {
        this.buffer.writeIndex(index);
    }

    @Override
    public ModelBuffer toBuffer() {
        return this.buffer;
    }
}

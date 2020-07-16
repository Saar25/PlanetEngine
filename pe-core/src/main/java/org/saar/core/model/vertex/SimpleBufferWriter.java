package org.saar.core.model.vertex;

public class SimpleBufferWriter extends AbstractModelBufferWriter<SimpleVertex> implements ModelBufferWriter<SimpleVertex> {

    private final ModelBuffer buffer;

    public SimpleBufferWriter(ModelBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    protected void writeVertex(SimpleVertex vertex) {
        for (float value : vertex.getFloatData()) {
            this.buffer.write(value);
        }
        for (int value : vertex.getIntData()) {
            this.buffer.write(value);
        }
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

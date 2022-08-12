package org.saar.lwjgl.opengl.attribute;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;

public class IntegerAttributeLinker implements AttributeLinker {

    private final int componentCount;
    private final DataType componentType;

    public IntegerAttributeLinker(int componentCount, DataType componentType) {
        this.componentCount = componentCount;
        this.componentType = componentType;
    }

    @Override
    public void link(int attributeIndex, int stride, int offset) {
        GL30.glVertexAttribIPointer(attributeIndex, this.componentCount,
                this.componentType.get(), stride, offset);
    }

    @Override
    public int getBytes() {
        return this.componentCount * this.componentType.getBytes();
    }
}

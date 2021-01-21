package org.saar.lwjgl.opengl.objects.attributes;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;

public class FloatAttributeLinker implements AttributeLinker {

    private final int componentCount;
    private final DataType componentType;
    private final boolean normalized;

    public FloatAttributeLinker(int componentCount, DataType componentType, boolean normalized) {
        this.componentCount = componentCount;
        this.componentType = componentType;
        this.normalized = normalized;
    }

    @Override
    public void link(int attributeIndex, int stride, int offset) {
        GL20.glVertexAttribPointer(attributeIndex, this.componentCount,
                this.componentType.get(), this.normalized, stride, offset);
    }

    @Override
    public int getBytes() {
        return this.componentCount * this.componentType.getBytes();
    }

}

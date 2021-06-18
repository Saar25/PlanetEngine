package org.saar.lwjgl.opengl.objects.attributes;

import org.lwjgl.opengl.GL20;

public class Attribute implements IAttribute {

    private final AttributeLinker linker;
    private final int attributeIndex;

    public Attribute(AttributeLinker linker, int attributeIndex) {
        this.linker = linker;
        this.attributeIndex = attributeIndex;
    }

    @Override
    public void enable() {
        GL20.glEnableVertexAttribArray(this.attributeIndex);
    }

    @Override
    public void disable() {
        GL20.glDisableVertexAttribArray(this.attributeIndex);
    }

    @Override
    public void link(int stride, int offset) {
        this.linker.link(this.attributeIndex, stride, offset);
    }

    @Override
    public int getBytesPerVertex() {
        return this.linker.getBytes();
    }
}

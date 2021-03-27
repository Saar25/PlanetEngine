package org.saar.lwjgl.opengl.objects.attributes;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.saar.lwjgl.opengl.constants.DataType;

public class Attribute {

    private final AttributeLinker linker;
    private final int attributeIndex;
    private final int instances;

    public Attribute(AttributeLinker linker, int attributeIndex) {
        this(linker, attributeIndex, 0);
    }

    public Attribute(AttributeLinker linker, int attributeIndex, int instances) {
        this.linker = linker;
        this.attributeIndex = attributeIndex;
        this.instances = instances;
    }

    public static Attribute of(int attributeIndex, int componentCount, DataType dataType, boolean normalized) {
        final AttributeLinker linker = new FloatAttributeLinker(
                componentCount, dataType, normalized);
        return new Attribute(linker, attributeIndex);
    }

    public static Attribute ofInstance(int attributeIndex, int componentCount, DataType dataType, boolean normalized) {
        final AttributeLinker linker = new FloatAttributeLinker(
                componentCount, dataType, normalized);
        return new Attribute(linker, attributeIndex, 1);
    }

    public static Attribute ofInstances(int attributeIndex, int componentCount, DataType dataType, boolean normalized, int instances) {
        final AttributeLinker linker = new FloatAttributeLinker(
                componentCount, dataType, normalized);
        return new Attribute(linker, attributeIndex, instances);
    }

    public static Attribute ofInteger(int attributeIndex, int componentCount, DataType dataType) {
        final AttributeLinker linker = new IntegerAttributeLinker(componentCount, dataType);
        return new Attribute(linker, attributeIndex);
    }

    public static Attribute ofIntegerInstance(int attributeIndex, int componentCount, DataType dataType) {
        final AttributeLinker linker = new IntegerAttributeLinker(componentCount, dataType);
        return new Attribute(linker, attributeIndex, 1);
    }

    public static Attribute ofIntegerInstances(int attributeIndex, int componentCount, DataType dataType, int instances) {
        final AttributeLinker linker = new IntegerAttributeLinker(componentCount, dataType);
        return new Attribute(linker, attributeIndex, instances);
    }

    public static int sumBytes(Attribute... attributes) {
        int sum = 0;
        for (Attribute attribute : attributes) {
            sum += attribute.getBytesPerVertex();
        }
        return sum;
    }

    private static void enable(int index) {
        GL20.glEnableVertexAttribArray(index);
    }

    private static void disable(int index) {
        GL20.glDisableVertexAttribArray(index);
    }

    public void enable() {
        Attribute.enable(this.attributeIndex);
    }

    public void disable() {
        Attribute.disable(this.attributeIndex);
    }

    public void link(int stride, int offset) {
        this.linker.link(this.attributeIndex, stride, offset);

        if (this.instances > 0) {
            GL33.glVertexAttribDivisor(this.attributeIndex, this.instances);
        }
    }

    public int getBytesPerVertex() {
        return this.linker.getBytes();
    }
}

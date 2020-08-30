package org.saar.lwjgl.opengl.objects;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.utils.GlConfigs;

public class Attribute {

    private final int attributeIndex;
    private final int componentCount;
    private final int componentsType;
    private final int bytesPerVertex;
    private final boolean normalized;
    private final int instances;

    private boolean enabled = false;

    private Attribute(int attributeIndex, int componentCount, DataType dataType, boolean normalized) {
        this(attributeIndex, componentCount, dataType, normalized, 0);
    }

    private Attribute(int attributeIndex, int componentCount, DataType dataType, boolean normalized, int instances) {
        this.attributeIndex = attributeIndex;
        this.componentCount = componentCount;
        this.componentsType = dataType.get();
        this.normalized = normalized;
        this.instances = instances;
        this.bytesPerVertex = dataType.getBytes() * componentCount;
    }

    public static Attribute of(int attributeIndex, int componentCount, DataType dataType, boolean normalized) {
        return new Attribute(attributeIndex, componentCount, dataType, normalized);
    }

    public static Attribute ofInstance(int attributeIndex, int componentCount,
                                       DataType dataType, boolean normalized) {
        return new Attribute(attributeIndex, componentCount, dataType, normalized, 1);
    }

    public static Attribute ofInstances(int attributeIndex, int componentCount,
                                        DataType dataType, boolean normalized, int instances) {
        return new Attribute(attributeIndex, componentCount, dataType, normalized, instances);
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
        if (GlConfigs.CACHE_STATE || !this.enabled) {
            Attribute.enable(this.attributeIndex);
            this.enabled = true;
        }
    }

    public void disable() {
        if (GlConfigs.CACHE_STATE || this.enabled) {
            Attribute.disable(this.attributeIndex);
            this.enabled = false;
        }
    }

    public void link(int stride, int offset) {
        GL20.glVertexAttribPointer(this.attributeIndex, this.componentCount,
                this.componentsType, this.normalized, stride, offset);
        if (this.instances > 0) {
            GL33.glVertexAttribDivisor(this.attributeIndex, this.instances);
        }
    }

    public int getAttributeIndex() {
        return this.attributeIndex;
    }

    public int getComponentCount() {
        return this.componentCount;
    }

    public int getComponentsType() {
        return this.componentsType;
    }

    public boolean isNormalized() {
        return this.normalized;
    }

    public int getBytesPerVertex() {
        return this.bytesPerVertex;
    }

    public int getInstances() {
        return this.instances;
    }
}

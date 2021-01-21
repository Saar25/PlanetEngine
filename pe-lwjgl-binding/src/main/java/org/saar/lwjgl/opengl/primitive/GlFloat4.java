package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.util.buffer.BufferWriter;
import org.saar.maths.utils.Vector4;

public class GlFloat4 extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 4;

    private final Vector4fc value;

    private GlFloat4(Vector4fc value) {
        this.value = value;
    }

    public static GlFloat4 of(float x, float y, float z, float w) {
        return new GlFloat4(Vector4.of(x, y, z, w));
    }

    public static GlFloat4 of(Vector4fc value) {
        return new GlFloat4(Vector4.of(value));
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform4f(location, getValue().x(), getValue().y(), getValue().z(), getValue().w());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized, int instances) {
        return new Attribute[]{Attribute.ofInstances(index, 4, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(BufferWriter buffer) {
        buffer.write(getValue().x());
        buffer.write(getValue().y());
        buffer.write(getValue().z());
        buffer.write(getValue().w());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector4fc getValue() {
        return this.value;
    }
}

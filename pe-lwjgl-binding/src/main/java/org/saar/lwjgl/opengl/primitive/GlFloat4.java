package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.maths.utils.Vector4;

public class GlFloat4 implements GlPrimitive {

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
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 3, DATA_TYPE, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = Float.floatToIntBits(getValue().x());
        buffer[index + 1] = Float.floatToIntBits(getValue().y());
        buffer[index + 2] = Float.floatToIntBits(getValue().z());
        buffer[index + 3] = Float.floatToIntBits(getValue().w());
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = getValue().x();
        buffer[index + 1] = getValue().y();
        buffer[index + 2] = getValue().z();
        buffer[index + 3] = getValue().w();
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

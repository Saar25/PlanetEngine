package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.maths.utils.Vector3;

public class GlFloat3 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 3;

    private final Vector3fc value;

    private GlFloat3(Vector3fc value) {
        this.value = value;
    }

    public static GlFloat3 of(float x, float y, float z) {
        return new GlFloat3(Vector3.of(x, y, z));
    }

    public static GlFloat3 of(Vector3fc value) {
        return new GlFloat3(Vector3.of(value));
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform3f(location, getValue().x(), getValue().y(), getValue().z());
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
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = getValue().x();
        buffer[index + 1] = getValue().y();
        buffer[index + 2] = getValue().z();
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector3fc getValue() {
        return this.value;
    }
}

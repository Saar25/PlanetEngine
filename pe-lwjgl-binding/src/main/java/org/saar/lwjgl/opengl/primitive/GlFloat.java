package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlFloat implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 1;

    private final float value;

    private GlFloat(float value) {
        this.value = value;
    }

    public static GlFloat of(float value) {
        return new GlFloat(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform1f(location, getValue());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 1, DATA_TYPE, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = Float.floatToIntBits(getValue());
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = getValue();
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public float getValue() {
        return this.value;
    }
}

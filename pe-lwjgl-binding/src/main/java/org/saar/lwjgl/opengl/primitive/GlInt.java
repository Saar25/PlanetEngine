package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.INT;
    private static final int COMPONENT_COUNT = 1;

    private final int value;

    private GlInt(int value) {
        this.value = value;
    }

    public static GlInt of(int value) {
        return new GlInt(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform1i(location, getValue());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 1, DATA_TYPE, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = getValue();
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

    public int getValue() {
        return this.value;
    }
}

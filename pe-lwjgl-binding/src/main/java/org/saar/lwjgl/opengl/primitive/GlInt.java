package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt implements GlPrimitive {

    private final int value;
    private final DataType dataType;

    private GlInt(int value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public static GlInt signed(int value) {
        return new GlInt(value, DataType.INT);
    }

    public static GlInt unsigned(int value) {
        return new GlInt(value, DataType.U_INT);
    }

    public static GlInt of(int value) {
        return GlInt.signed(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform1i(location, getValue());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 1, dataType, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = getValue();
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = getValue();
    }

    public int getValue() {
        return this.value;
    }
}

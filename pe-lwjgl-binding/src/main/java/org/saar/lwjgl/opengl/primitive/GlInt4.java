package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt4 implements GlPrimitive {

    private final Vector4ic value;

    private final DataType dataType;

    public GlInt4(Vector4ic value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public static GlInt4 signed(int x, int y, int z, int w) {
        return new GlInt4(new Vector4i(x, y, z, w), DataType.INT);
    }

    public static GlInt4 signed(Vector4ic value) {
        return new GlInt4(new Vector4i(value), DataType.INT);
    }

    public static GlInt4 unsigned(int x, int y, int z, int w) {
        return new GlInt4(new Vector4i(x, y, z, w), DataType.U_INT);
    }

    public static GlInt4 unsigned(Vector4ic value) {
        return new GlInt4(new Vector4i(value), DataType.U_INT);
    }

    public static GlInt4 of(int x, int y, int z, int w) {
        return GlInt4.signed(x, y, z, w);
    }

    public static GlInt4 of(Vector4ic value) {
        return GlInt4.signed(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform4i(location, value.x(), value.y(), value.z(), value.w());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 4, dataType, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
        buffer[index + 2] = value.z();
        buffer[index + 3] = value.w();
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
        buffer[index + 2] = value.z();
        buffer[index + 3] = value.w();
    }
}

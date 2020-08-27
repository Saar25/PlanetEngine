package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt2 implements GlPrimitive {

    private final Vector2ic value;

    private final DataType dataType;

    public GlInt2(Vector2ic value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public static GlInt2 signed(int x, int y) {
        return new GlInt2(new Vector2i(x, y), DataType.INT);
    }

    public static GlInt2 signed(Vector2ic value) {
        return new GlInt2(new Vector2i(value), DataType.INT);
    }

    public static GlInt2 unsigned(int x, int y) {
        return new GlInt2(new Vector2i(x, y), DataType.U_INT);
    }

    public static GlInt2 unsigned(Vector2ic value) {
        return new GlInt2(new Vector2i(value), DataType.U_INT);
    }

    public static GlInt2 of(int x, int y) {
        return GlInt2.signed(x, y);
    }

    public static GlInt2 of(Vector2ic value) {
        return GlInt2.signed(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform2f(location, value.x(), value.y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 2, dataType, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
    }
}

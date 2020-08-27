package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlUInt2 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.U_INT;

    private final Vector2ic value;

    public GlUInt2(Vector2ic value) {
        this.value = value;
    }

    public static GlUInt2 of(int x, int y) {
        return new GlUInt2(new Vector2i(x, y));
    }

    public static GlUInt2 of(Vector2ic value) {
        return new GlUInt2(value);
    }

    @Override
    public void loadUniform(int location) {
        GL30.glUniform2ui(location, value.x(), value.y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 2, DATA_TYPE, normalized)};
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

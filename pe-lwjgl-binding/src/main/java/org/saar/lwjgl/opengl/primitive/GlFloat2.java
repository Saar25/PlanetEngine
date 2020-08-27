package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.maths.utils.Vector2;

public class GlFloat2 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;

    private final Vector2fc value;

    public GlFloat2(Vector2fc value) {
        this.value = value;
    }

    public static GlFloat2 of(float x, float y) {
        return new GlFloat2(Vector2.of(x, y));
    }

    public static GlFloat2 of(Vector2fc value) {
        return new GlFloat2(Vector2.of(value));
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform2f(location, value.x(), value.y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 2, DATA_TYPE, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = Float.floatToIntBits(value.x());
        buffer[index + 1] = Float.floatToIntBits(value.y());
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
    }
}

package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.maths.utils.Vector2;

public class GlFloat2 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 2;

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
        GL20.glUniform2f(location, getValue().x(), getValue().y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized, int instances) {
        return new Attribute[]{Attribute.ofInstances(index, 2, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(BufferWriter buffer) {
        buffer.write(getValue().x());
        buffer.write(getValue().y());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector2fc getValue() {
        return this.value;
    }
}

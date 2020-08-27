package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class GlInt2 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.INT;
    private static final int COMPONENT_COUNT = 2;

    private final Vector2ic value;

    public GlInt2(Vector2ic value) {
        this.value = value;
    }

    public static GlInt2 of(int x, int y) {
        return new GlInt2(new Vector2i(x, y));
    }

    public static GlInt2 of(Vector2ic value) {
        return new GlInt2(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform2i(location, getValue().x(), getValue().y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 2, DATA_TYPE, normalized)};
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

    public Vector2ic getValue() {
        return this.value;
    }
}

package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt4 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.INT;
    private static final int COMPONENT_COUNT = 4;

    private final Vector4ic value;

    public GlInt4(Vector4ic value) {
        this.value = value;
    }

    public static GlInt4 of(int x, int y, int z, int w) {
        return new GlInt4(new Vector4i(x, y, z, w));
    }

    public static GlInt4 of(Vector4ic value) {
        return new GlInt4(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform4i(location, value.x(), value.y(), value.z(), value.w());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 4, DATA_TYPE, normalized)};
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

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }
}

package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt3 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.INT;
    private static final int COMPONENT_COUNT = 3;

    private final Vector3ic value;

    public GlInt3(Vector3ic value) {
        this.value = value;
    }

    public static GlInt3 of(int x, int y, int z) {
        return new GlInt3(new Vector3i(x, y, z));
    }

    public static GlInt3 of(Vector3ic value) {
        return new GlInt3(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform3i(location, value.x(), value.y(), value.z());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 3, DATA_TYPE, normalized)};
    }

    @Override
    public void write(int index, int[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
        buffer[index + 2] = value.z();
    }

    @Override
    public void write(int index, float[] buffer) {
        buffer[index] = value.x();
        buffer[index + 1] = value.y();
        buffer[index + 2] = value.z();
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

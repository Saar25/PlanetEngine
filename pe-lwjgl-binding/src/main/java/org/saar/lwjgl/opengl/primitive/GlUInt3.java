package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlUInt3 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.U_INT;
    private static final int COMPONENT_COUNT = 3;

    private final Vector3ic value;

    public GlUInt3(Vector3ic value) {
        this.value = value;
    }

    public static GlUInt3 of(int x, int y, int z) {
        return new GlUInt3(new Vector3i(x, y, z));
    }

    public static GlUInt3 of(Vector3ic value) {
        return new GlUInt3(value);
    }

    @Override
    public void loadUniform(int location) {
        GL30.glUniform3ui(location, value.x(), value.y(), value.z());
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

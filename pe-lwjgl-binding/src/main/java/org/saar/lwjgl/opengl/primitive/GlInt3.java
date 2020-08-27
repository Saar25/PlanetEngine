package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class GlInt3 implements GlPrimitive {

    private final Vector3ic value;

    private final DataType dataType;

    public GlInt3(Vector3ic value, DataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public static GlInt3 signed(int x, int y, int z) {
        return new GlInt3(new Vector3i(x, y, z), DataType.INT);
    }

    public static GlInt3 signed(Vector3ic value) {
        return new GlInt3(new Vector3i(value), DataType.INT);
    }

    public static GlInt3 unsigned(int x, int y, int z) {
        return new GlInt3(new Vector3i(x, y, z), DataType.U_INT);
    }

    public static GlInt3 unsigned(Vector3ic value) {
        return new GlInt3(new Vector3i(value), DataType.U_INT);
    }

    public static GlInt3 of(int x, int y, int z) {
        return GlInt3.signed(x, y, z);
    }

    public static GlInt3 of(Vector3ic value) {
        return GlInt3.signed(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform2f(location, value.x(), value.y());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized) {
        return new Attribute[]{Attribute.of(index, 3, dataType, normalized)};
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

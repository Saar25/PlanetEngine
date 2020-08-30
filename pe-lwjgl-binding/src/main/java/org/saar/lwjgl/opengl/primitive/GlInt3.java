package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class GlInt3 extends GlPrimitiveBase implements GlPrimitive {

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
        GL20.glUniform3i(location, getValue().x(), getValue().y(), getValue().z());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized, int instances) {
        return new Attribute[]{Attribute.ofInstances(index, 3, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(BufferWriter buffer) {
        buffer.write(getValue().x());
        buffer.write(getValue().y());
        buffer.write(getValue().z());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector3ic getValue() {
        return this.value;
    }
}

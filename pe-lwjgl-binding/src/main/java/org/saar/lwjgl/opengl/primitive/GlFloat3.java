package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.util.buffer.BufferWriter;
import org.saar.maths.utils.Vector3;

public class GlFloat3 extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 3;

    private final Vector3fc value;

    private GlFloat3(Vector3fc value) {
        this.value = value;
    }

    public static GlFloat3 of(float x, float y, float z) {
        return new GlFloat3(Vector3.of(x, y, z));
    }

    public static GlFloat3 of(Vector3fc value) {
        return new GlFloat3(Vector3.of(value));
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform3f(location, getValue().x(), getValue().y(), getValue().z());
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

    public Vector3fc getValue() {
        return this.value;
    }
}

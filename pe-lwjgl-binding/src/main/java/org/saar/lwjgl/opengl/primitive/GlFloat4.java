package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.util.DataWriter;
import org.saar.maths.utils.Vector4;

public class GlFloat4 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 4;

    private final Vector4fc value;

    private GlFloat4(Vector4fc value) {
        this.value = value;
    }

    public static GlFloat4 of(float x, float y, float z, float w) {
        return new GlFloat4(Vector4.of(x, y, z, w));
    }

    public static GlFloat4 of(Vector4fc value) {
        return new GlFloat4(Vector4.of(value));
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform4f(location, getValue().x(), getValue().y(), getValue().z(), getValue().w());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofInstanced(index, 4, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.write4f(getValue());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector4fc getValue() {
        return this.value;
    }
}

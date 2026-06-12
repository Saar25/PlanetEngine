package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.util.DataWriter;

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
        GL20.glUniform4i(location, getValue().x(), getValue().y(), getValue().z(), getValue().w());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofIntegerInstanced(index, 4, DATA_TYPE, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.write4i(getValue());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public Vector4ic getValue() {
        return this.value;
    }
}

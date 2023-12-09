package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector4i;
import org.joml.Vector4ic;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.util.DataWriter;

public class GlUInt4 implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.U_INT;
    private static final int COMPONENT_COUNT = 4;

    private final Vector4ic value;

    public GlUInt4(Vector4ic value) {
        this.value = value;
    }

    public static GlUInt4 of(int x, int y, int z, int w) {
        return new GlUInt4(new Vector4i(x, y, z, w));
    }

    public static GlUInt4 of(Vector4ic value) {
        return new GlUInt4(value);
    }

    @Override
    public void loadUniform(int location) {
        GL30.glUniform4ui(location, getValue().x(), getValue().y(), getValue().z(), getValue().w());
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

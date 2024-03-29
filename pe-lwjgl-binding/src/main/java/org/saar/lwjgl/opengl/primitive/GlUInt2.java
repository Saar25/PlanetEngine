package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.util.DataWriter;

public class GlUInt2 extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.U_INT;
    private static final int COMPONENT_COUNT = 2;

    private final Vector2ic value;

    public GlUInt2(Vector2ic value) {
        this.value = value;
    }

    public static GlUInt2 of(int x, int y) {
        return new GlUInt2(new Vector2i(x, y));
    }

    public static GlUInt2 of(Vector2ic value) {
        return new GlUInt2(value);
    }

    @Override
    public void loadUniform(int location) {
        GL30.glUniform2ui(location, getValue().x(), getValue().y());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofIntegerInstanced(index, 2, DATA_TYPE, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.write2i(getValue());
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

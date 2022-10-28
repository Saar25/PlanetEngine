package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.util.DataWriter;

public class GlUInt3 extends GlPrimitiveBase implements GlPrimitive {

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
        GL30.glUniform3ui(location, getValue().x(), getValue().y(), getValue().z());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofIntegerInstanced(index, 3, DATA_TYPE, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.write3i(getValue());
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

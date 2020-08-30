package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class GlUInt extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.U_INT;
    private static final int COMPONENT_COUNT = 1;

    private final int value;

    private GlUInt(int value) {
        this.value = value;
    }

    public static GlUInt of(int value) {
        return new GlUInt(value);
    }

    @Override
    public void loadUniform(int location) {
        GL30.glUniform1ui(location, getValue());
    }

    @Override
    public Attribute[] attribute(int index, boolean normalized, int instances) {
        return new Attribute[]{Attribute.ofInstances(index, 1, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(BufferWriter buffer) {
        buffer.write(getValue());
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    public int getValue() {
        return this.value;
    }
}

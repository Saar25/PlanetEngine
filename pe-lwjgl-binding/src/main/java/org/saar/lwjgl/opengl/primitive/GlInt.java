package org.saar.lwjgl.opengl.primitive;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;
import org.saar.lwjgl.util.DataWriter;

public class GlInt extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.INT;
    private static final int COMPONENT_COUNT = 1;

    private final int value;

    private GlInt(int value) {
        this.value = value;
    }

    public static GlInt of(int value) {
        return new GlInt(value);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniform1i(location, getValue());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofIntegerInstanced(index, 1, DATA_TYPE, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.writeInt(getValue());
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

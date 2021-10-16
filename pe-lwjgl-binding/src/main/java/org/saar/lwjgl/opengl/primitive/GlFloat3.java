package org.saar.lwjgl.opengl.primitive;

import org.joml.Vector3fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;
import org.saar.lwjgl.util.DataWriter;
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
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{Attributes.ofInstanced(index, 3, DATA_TYPE, normalized, instances)};
    }

    @Override
    public void write(DataWriter writer) {
        writer.write3f(getValue());
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

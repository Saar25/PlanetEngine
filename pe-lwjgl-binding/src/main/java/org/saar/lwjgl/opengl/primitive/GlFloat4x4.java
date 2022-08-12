package org.saar.lwjgl.opengl.primitive;

import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.util.DataWriter;
import org.saar.maths.utils.Matrix4;

public class GlFloat4x4 extends GlPrimitiveBase implements GlPrimitive {

    private static final DataType DATA_TYPE = DataType.FLOAT;
    private static final int COMPONENT_COUNT = 16;

    private final Matrix4fc value;
    private final float[] buffer;

    private GlFloat4x4(Matrix4fc value, float[] buffer) {
        this.value = value;
        this.buffer = buffer;
    }

    public static GlFloat4x4 of(Matrix4fc value) {
        final float[] buffer = value.get(new float[16]);
        return new GlFloat4x4(Matrix4.of(value), buffer);
    }

    @Override
    public void loadUniform(int location) {
        GL20.glUniformMatrix4fv(location, false, getBuffer());
    }

    @Override
    public IAttribute[] attribute(int index, boolean normalized, int instances) {
        return new IAttribute[]{
                Attributes.ofInstanced(index, 4, DATA_TYPE, normalized, instances),
                Attributes.ofInstanced(index + 1, 4, DATA_TYPE, normalized, instances),
                Attributes.ofInstanced(index + 2, 4, DATA_TYPE, normalized, instances),
                Attributes.ofInstanced(index + 3, 4, DATA_TYPE, normalized, instances)
        };
    }

    @Override
    public void write(DataWriter writer) {
        writer.write4x4f(this.value);
    }

    @Override
    public DataType getDataType() {
        return DATA_TYPE;
    }

    @Override
    public int getComponentCount() {
        return COMPONENT_COUNT;
    }

    private float[] getBuffer() {
        return this.buffer;
    }

    public Matrix4fc getValue() {
        return this.value;
    }
}

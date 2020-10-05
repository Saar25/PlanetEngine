package org.saar.lwjgl.opengl.shaders.uniforms2;

public class FloatUniformValue extends FloatUniform implements UniformValue<Float> {

    private final String name;

    private float value = 0;

    public FloatUniformValue(String name) {
        this.name = name;
    }

    @Override
    public final float getUniformValue() {
        return get();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Float getValue() {
        return get();
    }

    @Override
    public final void setValue(Float value) {
        set(value);
    }

    public final float get() {
        return this.value;
    }

    public final void set(float value) {
        this.value = value;
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms2;

public class IntUniformValue extends IntUniform implements UniformValue<Integer> {

    private final String name;

    private int value = 0;

    public IntUniformValue(String name) {
        this.name = name;
    }

    @Override
    public final int getUniformValue() {
        return get();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Integer getValue() {
        return get();
    }

    @Override
    public final void setValue(Integer value) {
        set(value);
    }

    public final int get() {
        return this.value;
    }

    public final void set(int value) {
        this.value = value;
    }
}

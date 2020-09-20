package org.saar.lwjgl.opengl.shaders.uniforms2;

public class FloatUniformConstant extends FloatUniform implements Uniform {

    private final String name;
    private final float value;

    public FloatUniformConstant(String name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public float getUniformValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

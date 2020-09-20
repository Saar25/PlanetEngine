package org.saar.lwjgl.opengl.shaders.uniforms2;

public class IntUniformConstant extends IntUniform implements Uniform {

    private final String name;
    private final int value;

    public IntUniformConstant(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public int getUniformValue() {
        return this.value;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public abstract class AbstractUniformProperty<T> implements UniformProperty<T> {

    private final String name;
    private int location = -1;
    private boolean exists = true;

    AbstractUniformProperty(String name) {
        this.name = name;
    }

    @Override
    public final void initialize(ShadersProgram shadersProgram) {
        if ((location = shadersProgram.getUniformLocation(name)) == -1) {
            System.err.println("Cannot locate uniform " + name);
            exists = false;
        }
    }

    int getLocation() {
        return this.location;
    }

    public String getName() {
        return this.name;
    }

    public boolean isExists() {
        return this.exists;
    }
}

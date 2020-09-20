package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public abstract class UniformBase implements Uniform {

    private int location = Uniform.NOT_FOUND;

    @Override
    public final void initialize(ShadersProgram shadersProgram) {
        this.location = shadersProgram.getUniformLocation(getName());
        if (!isFound()) {
            System.err.println("Cannot initialize uniform " + getName());
        }
    }

    @Override
    public final void load() {
        if (isFound()) {
            doLoad();
        }
    }

    public final boolean isFound() {
        return getLocation() != Uniform.NOT_FOUND;
    }

    protected final int getLocation() {
        return this.location;
    }

    public abstract void doLoad();

    public abstract String getName();
}

package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.ShadersProgram;

public abstract class UniformBase implements Uniform {

    private static final int NOT_FOUND = -1;

    private int location = UniformBase.NOT_FOUND;

    @Override
    public final void initialize(ShadersProgram shadersProgram) {
        this.location = shadersProgram.getUniformLocation(getName());
        if (!isFound()) {
            System.err.println("Cannot initialize uniform " + getName());
        } else {
            doInitialize();
        }
    }

    @Override
    public final void load() {
        if (isFound()) {
            doLoad();
        }
    }

    public final boolean isFound() {
        return getLocation() != UniformBase.NOT_FOUND;
    }

    protected final int getLocation() {
        return this.location;
    }

    public void doInitialize() {

    }

    public abstract void doLoad();

    public abstract String getName();
}

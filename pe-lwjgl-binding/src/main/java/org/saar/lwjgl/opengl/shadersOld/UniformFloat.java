package org.saar.lwjgl.opengl.shadersOld;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlConfigs;

public class UniformFloat extends Uniform<Float> {

    private float value;

    private UniformFloat(int location) {
        super(location, null, null, 0f);
    }

    public static UniformFloat create(ShadersProgram shadersProgram, String uniformName) {
        int location = getLocation(shadersProgram, uniformName);
        return new UniformFloat(location);
    }

    @Override
    public void load(Float newValue) {
        load(newValue.floatValue());
    }

    public void load(float newValue) {
        if (!GlConfigs.CACHE_STATE || this.value != newValue) {
            GL20.glUniform1f(location, newValue);
            this.value = newValue;
        }
    }

}

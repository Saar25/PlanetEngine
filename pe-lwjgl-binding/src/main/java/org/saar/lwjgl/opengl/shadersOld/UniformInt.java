package org.saar.lwjgl.opengl.shadersOld;

import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlConfigs;

public class UniformInt extends Uniform<Integer> {

    private int value;

    private UniformInt(int location) {
        super(location, null, null, 0);
    }

    public static UniformInt create(ShadersProgram shadersProgram, String uniformName) {
        int location = getLocation(shadersProgram, uniformName);
        return new UniformInt(location);
    }

    @Override
    public void load(Integer newValue) {
        load(newValue.intValue());
    }

    public void load(int newValue) {
        if (GlConfigs.CACHE_STATE || this.value != newValue) {
            GL20.glUniform1i(location, newValue);
            this.value = newValue;
        }
    }

}

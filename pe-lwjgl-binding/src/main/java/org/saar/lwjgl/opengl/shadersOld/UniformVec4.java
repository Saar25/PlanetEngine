package org.saar.lwjgl.opengl.shadersOld;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.utils.GlConfigs;
import org.saar.maths.utils.Vector4;

public class UniformVec4 extends Uniform<Vector4f> {

    private UniformVec4(int location) {
        super(location, null, null, Vector4.create());
    }

    public static UniformVec4 create(ShadersProgram shadersProgram, String uniformName) {
        int location = getLocation(shadersProgram, uniformName);
        return new UniformVec4(location);
    }

    @Override
    public void load(Vector4f newValue) {
        load(newValue.x, newValue.y, newValue.z, newValue.w);
    }

    public void load(float x, float y, float z, float w) {
        if (!GlConfigs.CACHE_STATE || !equals(x, y, z, w)) {
            GL20.glUniform4f(location, x, y, z, w);
            value.set(x, y, z, w);
        }
    }

    private boolean equals(float x, float y, float z, float w) {
        return value.x == x && value.y == y && value.z == z && value.w == w;
    }
}

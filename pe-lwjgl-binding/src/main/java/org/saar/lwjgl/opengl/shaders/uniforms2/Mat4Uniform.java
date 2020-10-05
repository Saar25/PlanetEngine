package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.joml.Matrix4fc;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public abstract class Mat4Uniform extends UniformBase implements Uniform {

    @Override
    public final void doLoad() {
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            final FloatBuffer buffer = stack.mallocFloat(16);
            GL20.glUniformMatrix4fv(getLocation(), isTranspose(),
                    getUniformValue().get(buffer));
        }
    }

    public boolean isTranspose() {
        return false;
    }

    public abstract Matrix4fc getUniformValue();
}

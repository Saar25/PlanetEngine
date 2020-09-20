package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;

public interface InstanceUniform<T> extends Uniform {

    void update(InstanceRenderState<T> state);

}

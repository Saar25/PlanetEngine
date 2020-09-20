package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.lwjgl.opengl.shaders.StageRenderState;

public interface StageUniform extends Uniform {

    void update(StageRenderState state);

}

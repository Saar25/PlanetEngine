package org.saar.core.renderer;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;

public interface InstanceUniformUpdater<T> {

    void update(InstanceRenderState<T> state);

}

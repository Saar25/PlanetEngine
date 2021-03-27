package org.saar.core.renderer.uniforms;

import org.saar.core.renderer.RenderState;

public interface UniformUpdater<T> {

    void update(RenderState<T> state);

}

package org.saar.core.renderer;

import org.saar.core.mesh.Model;

public interface RendererPrototype<T extends Model> {

    default void onRenderCycle(RenderContext context) {
    }

    default void onInstanceDraw(RenderContext context, RenderState<T> state) {
    }

}

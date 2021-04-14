package org.saar.core.renderer;

import org.saar.core.mesh.Model;

public interface RendererPrototype<T extends Model> {

    default String[] vertexAttributes() {
        return new String[0];
    }

    default String[] fragmentOutputs() {
        return new String[0];
    }

    default void onRenderCycle(RenderContext context) {
    }

    default void onInstanceDraw(RenderContext context, T state) {
    }

}

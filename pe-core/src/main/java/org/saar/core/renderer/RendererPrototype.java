package org.saar.core.renderer;

public interface RendererPrototype<T> {

    default String[] vertexAttributes() {
        return new String[0];
    }

    default String[] fragmentOutputs() {
        return new String[0];
    }

    default void onRenderCycle(RenderContext context) {
    }

    default void onInstanceDraw(RenderContext context, T instance) {
    }

    void doInstanceDraw(RenderContext context, T instance);

}

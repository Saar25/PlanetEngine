package org.saar.core.renderer.deferred;

public interface RenderPass {

    void render(DeferredRenderingBuffers buffers);

    void delete();

}

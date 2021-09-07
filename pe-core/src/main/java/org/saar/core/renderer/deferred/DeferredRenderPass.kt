package org.saar.core.renderer.deferred;

import org.saar.core.renderer.renderpass.RenderPass;
import org.saar.core.renderer.renderpass.RenderPassContext;

public interface DeferredRenderPass extends RenderPass {

    void render(RenderPassContext context, DeferredRenderingBuffers buffers);

}

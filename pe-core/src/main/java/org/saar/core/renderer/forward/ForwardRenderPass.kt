package org.saar.core.renderer.forward;

import org.saar.core.renderer.renderpass.RenderPass;
import org.saar.core.renderer.renderpass.RenderPassContext;

public interface ForwardRenderPass extends RenderPass {

    void render(RenderPassContext context, ForwardRenderingBuffers buffers);

}

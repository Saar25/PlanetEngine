package org.saar.core.renderer.pbr;

import org.saar.core.renderer.renderpass.RenderPass;
import org.saar.core.renderer.renderpass.RenderPassContext;

public interface PBRenderPass extends RenderPass {

    void render(RenderPassContext context, PBRenderingBuffers buffers);

}

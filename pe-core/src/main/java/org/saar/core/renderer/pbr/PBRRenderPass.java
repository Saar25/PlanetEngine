package org.saar.core.renderer.pbr;

import org.saar.core.renderer.renderpass.RenderPass;
import org.saar.core.renderer.renderpass.RenderPassContext;

public interface PBRRenderPass extends RenderPass {

    void renderPBR(RenderPassContext context, PBRRenderingBuffers buffers);

}

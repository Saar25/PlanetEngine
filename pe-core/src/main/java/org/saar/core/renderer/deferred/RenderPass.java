package org.saar.core.renderer.deferred;

public interface RenderPass {

    void render(RenderPassContext context);

    void delete();

}

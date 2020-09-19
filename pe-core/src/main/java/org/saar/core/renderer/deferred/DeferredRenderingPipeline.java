package org.saar.core.renderer.deferred;

public class DeferredRenderingPipeline {

    private RenderPassesHelper helper = RenderPassesHelper.empty();

    public void addRenderPass(RenderPass renderPass) {
        this.helper = this.helper.addRenderPass(renderPass);
    }

    public void removeRenderPass(RenderPass renderPass) {
        this.helper = this.helper.removeRenderPass(renderPass);
    }

    public void render(DeferredRenderingBuffers buffers) {
        this.helper.render(buffers);
    }

    public void delete() {
        this.helper.delete();
    }

}

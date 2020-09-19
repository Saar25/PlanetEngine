package org.saar.core.renderer.deferred;

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class DeferredRenderingPipeline {

    private RenderPassesHelper helper = RenderPassesHelper.empty();

    public void addRenderPass(RenderPass renderPass) {
        this.helper = this.helper.addRenderPass(renderPass);
    }

    public void removeRenderPass(RenderPass renderPass) {
        this.helper = this.helper.removeRenderPass(renderPass);
    }

    public void render(ReadOnlyTexture image) {
        this.helper.render(image);
    }

    public void delete() {
        this.helper.delete();
    }

}

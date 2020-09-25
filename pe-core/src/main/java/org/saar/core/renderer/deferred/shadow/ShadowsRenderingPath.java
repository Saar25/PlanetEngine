package org.saar.core.renderer.deferred.shadow;

import org.saar.core.renderer.Renderer;
import org.saar.core.renderer.RenderersHelper;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public class ShadowsRenderingPath implements RenderingPath {

    private RenderersHelper helper = RenderersHelper.empty();

    private final ShadowsScreenPrototype prototype = new ShadowsScreenPrototype();

    private final OffScreen screen;

    public ShadowsRenderingPath(int imageSize) {
        final Fbo fbo = Fbo.create(imageSize, imageSize);
        this.screen = Screens.fromPrototype(this.prototype, fbo);
    }

    public void addRenderer(final Renderer renderer) {
        this.helper = this.helper.addRenderer(renderer);
    }

    public void removeRenderer(final Renderer renderer) {
        this.helper = this.helper.removeRenderer(renderer);
    }

    public ReadOnlyTexture getShadowMap() {
        return this.prototype.getDepthTexture();
    }

    @Override
    public void render() {
        this.screen.setAsDraw();
        this.helper.render();
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.helper.delete();
    }
}

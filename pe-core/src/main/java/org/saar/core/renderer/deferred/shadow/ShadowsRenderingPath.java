package org.saar.core.renderer.deferred.shadow;

import org.saar.core.renderer.Renderer;
import org.saar.core.renderer.RenderersHelper;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter;
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ShadowsRenderingPath implements RenderingPath {

    private RenderersHelper helper = RenderersHelper.empty();

    private final ShadowsScreenPrototype prototype = new ShadowsScreenPrototype();

    private final OffScreen screen;

    public ShadowsRenderingPath(int imageSize) {
        final Fbo fbo = Fbo.create(imageSize, imageSize);
        this.screen = Screens.fromPrototype(this.prototype, fbo);
        this.prototype.getDepthTexture().setSettings(TextureTarget.TEXTURE_2D,
                new TextureMinFilterSetting(MinFilterParameter.LINEAR),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureSWrapSetting(WrapParameter.CLAMP_TO_EDGE),
                new TextureTWrapSetting(WrapParameter.CLAMP_TO_EDGE));
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
        GlUtils.clear(GlBuffer.DEPTH);
        this.helper.render();
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.helper.delete();
    }
}

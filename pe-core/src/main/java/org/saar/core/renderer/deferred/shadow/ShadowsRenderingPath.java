package org.saar.core.renderer.deferred.shadow;

import org.joml.Vector3fc;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.light.IDirectionalLight;
import org.saar.core.renderer.RenderContextBase;
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
import org.saar.lwjgl.opengl.utils.GlCullFace;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ShadowsRenderingPath implements RenderingPath {

    private final ShadowsScreenPrototype prototype = new ShadowsScreenPrototype();
    private final ShadowsCamera camera;
    private final OffScreen screen;
    private RenderersHelper helper = RenderersHelper.empty();

    public ShadowsRenderingPath(ShadowsQuality quality, OrthographicProjection projection, IDirectionalLight light) {
        this.camera = camera(projection, light.getDirection());

        final Fbo fbo = Fbo.create(quality.getImageSize(), quality.getImageSize());
        this.screen = Screens.fromPrototype(this.prototype, fbo);
        this.prototype.getDepthTexture().setSettings(TextureTarget.TEXTURE_2D,
                new TextureMinFilterSetting(MinFilterParameter.LINEAR),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureSWrapSetting(WrapParameter.CLAMP_TO_EDGE),
                new TextureTWrapSetting(WrapParameter.CLAMP_TO_EDGE));
    }

    private static ShadowsCamera camera(OrthographicProjection projection, Vector3fc direction) {
        final ShadowsCamera shadowsCamera = new ShadowsCamera(projection);
        shadowsCamera.getTransform().getRotation().lookAlong(direction);
        shadowsCamera.getTransform().getPosition().set(0, 0, 0);
        return shadowsCamera;
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

    public ShadowsCamera getCamera() {
        return this.camera;
    }

    @Override
    public ShadowRenderingOutput render() {
        final RenderContextBase context = new RenderContextBase(getCamera());
        context.getHints().cullFace = GlCullFace.FRONT;

        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.DEPTH);
        this.helper.render(context);

        return new ShadowRenderingOutput(this.screen);
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.helper.delete();
    }
}

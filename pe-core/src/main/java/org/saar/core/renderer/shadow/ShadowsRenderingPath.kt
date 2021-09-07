package org.saar.core.renderer.shadow;

import org.joml.Vector3fc;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.light.IDirectionalLight;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
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

    private final ShadowsScreenPrototype prototype;
    private final ShadowsCamera camera;
    private final OffScreen screen;

    private final ShadowsRenderNode renderNode;

    public ShadowsRenderingPath(ShadowsQuality quality, OrthographicProjection projection,
                                IDirectionalLight light, ShadowsRenderNode renderNode) {
        this(new ShadowsScreenPrototypeDefault(), quality, projection, light, renderNode);
    }

    public ShadowsRenderingPath(ShadowsScreenPrototype prototype, ShadowsQuality quality,
                                OrthographicProjection projection, IDirectionalLight light,
                                ShadowsRenderNode renderNode) {
        this.prototype = prototype;
        this.camera = camera(projection, light.getDirection());
        this.renderNode = renderNode;

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

    public ShadowsCamera getCamera() {
        return this.camera;
    }

    @Override
    public ShadowRenderingOutput render() {
        this.screen.setAsDraw();
        GlUtils.clear(GlBuffer.DEPTH);

        final RenderContextBase context = new RenderContextBase(this.camera);
        context.getHints().cullFace = GlCullFace.FRONT;
        this.renderNode.renderShadows(context);

        return new ShadowRenderingOutput(this.screen, this.prototype.getDepthTexture());
    }

    @Override
    public void delete() {
        this.renderNode.delete();
        this.screen.delete();
    }
}

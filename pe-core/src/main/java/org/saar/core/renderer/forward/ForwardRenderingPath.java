package org.saar.core.renderer.forward;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ForwardRenderingPath implements RenderingPath {

    private final ForwardScreenPrototype prototype;
    private final OffScreen screen;
    private final ICamera camera;
    private final ForwardRenderNode renderNode;

    public ForwardRenderingPath(ICamera camera, ForwardRenderNode renderNode) {
        this(new ForwardScreenPrototypeDefault(), camera, renderNode);
    }

    public ForwardRenderingPath(ForwardScreenPrototype prototype, ICamera camera, ForwardRenderNode renderNode) {
        this.prototype = prototype;
        this.screen = Screens.fromPrototype(prototype, Fbo.create(0, 0));

        this.camera = camera;
        this.renderNode = renderNode;
    }

    @Override
    public ForwardRenderingOutput render() {
        this.screen.resizeToMainScreen();

        this.screen.setAsDraw();

        GlUtils.clearColourAndDepthBuffer();

        this.renderNode.renderForward(new RenderContextBase(this.camera));

        return new ForwardRenderingOutput(this.screen,
                this.prototype.getColourTexture(),
                this.prototype.getDepthTexture());
    }

    @Override
    public void delete() {
        this.renderNode.delete();
        this.screen.delete();
    }
}

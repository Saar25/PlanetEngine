package org.saar.core.renderer.forward;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ForwardRenderingPath implements RenderingPath {

    private final ForwardScreenPrototype prototype = new ForwardScreenPrototype();

    private final OffScreen screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0));

    private final ICamera camera;
    private final ForwardRenderNode renderNode;

    public ForwardRenderingPath(ICamera camera, ForwardRenderNode renderNode) {
        this.camera = camera;
        this.renderNode = renderNode;
    }

    @Override
    public ForwardRenderingOutput render() {
        this.screen.resizeToMainScreen();

        this.screen.setAsDraw();

        GlUtils.clearColourAndDepthBuffer();

        this.renderNode.renderForward(new RenderContextBase(this.camera));

        return new ForwardRenderingOutput(this.screen, this.prototype.asBuffers());
    }

    @Override
    public void delete() {
        this.renderNode.delete();
        this.screen.delete();
    }
}

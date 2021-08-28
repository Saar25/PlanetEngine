package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.postprocessing.PostProcessingBuffers;
import org.saar.core.postprocessing.PostProcessingPipeline;
import org.saar.core.renderer.RenderContextBase;
import org.saar.core.renderer.RenderingOutput;
import org.saar.core.renderer.RenderingPath;
import org.saar.core.screen.OffScreen;
import org.saar.core.screen.Screens;
import org.saar.lwjgl.opengl.constants.Comparator;
import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.stencil.*;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class DeferredRenderingPath implements RenderingPath {

    private final DeferredScreenPrototype prototype = new DeferredScreenPrototype();

    private final OffScreen screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0));

    private final StencilState stencilState = new StencilState(StencilOperation.REPLACE_ON_PASS,
            new StencilFunction(Comparator.ALWAYS, 1, 0xFF), StencilMask.UNCHANGED);

    private final ICamera camera;
    private final DeferredRenderNode renderNode;
    private final DeferredRenderPassesPipeline pipeline;
    private final PostProcessingPipeline postProcessingPipeline;

    public DeferredRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                 DeferredRenderPassesPipeline pipeline) {
        this(camera, renderNode, pipeline, null);
    }

    public DeferredRenderingPath(ICamera camera, DeferredRenderNode renderNode,
                                 DeferredRenderPassesPipeline pipeline,
                                 PostProcessingPipeline postProcessingPipeline) {
        this.camera = camera;
        this.renderNode = renderNode;
        this.pipeline = pipeline;
        this.postProcessingPipeline = postProcessingPipeline;
    }

    @Override
    public RenderingOutput render() {
        this.screen.setAsDraw();
        this.screen.resizeToMainScreen();

        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL);

        StencilTest.apply(this.stencilState);

        this.renderNode.renderDeferred(new RenderContextBase(this.camera));

        final DeferredRenderingOutput pipelineOutput = this.pipeline
                .process(this.camera, this.prototype.asBuffers(), screen);

        if (this.postProcessingPipeline != null) {
            final PostProcessingBuffers buffers = pipelineOutput.asPostProcessingInput();

            return this.postProcessingPipeline.process(camera, buffers, this.screen);
        } else {
            return pipelineOutput;
        }
    }

    @Override
    public void delete() {
        this.screen.delete();
        this.renderNode.delete();
        this.pipeline.delete();
    }
}

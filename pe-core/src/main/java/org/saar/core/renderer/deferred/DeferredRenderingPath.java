package org.saar.core.renderer.deferred;

import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderingPath;

public class DeferredRenderingPath implements RenderingPath {

    private final ICamera camera;
    private final RenderPassesPipeline pipeline;
    private final DeferredRenderingBuffers buffers;

    public DeferredRenderingPath(ICamera camera, DeferredRenderingBuffers buffers, RenderPassesPipeline pipeline) {
        this.camera = camera;
        this.pipeline = pipeline;
        this.buffers = buffers;
    }

    @Override
    public DeferredRenderingOutput render() {
        return this.pipeline.process(this.camera, this.buffers);
    }

    @Override
    public void delete() {
        this.pipeline.delete();
    }
}

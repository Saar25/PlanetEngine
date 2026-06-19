package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public class RenderContext {

    private final ICamera camera;

    public RenderContext() {
        this.camera = null;
    }

    public RenderContext(ICamera camera) {
        this.camera = camera;
    }

    public RenderContext(RenderContext context) {
        this.camera = context.camera;
    }

    public ICamera getCamera() {
        return camera;
    }
}

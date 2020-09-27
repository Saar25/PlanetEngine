package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public class RenderContextBase extends RenderContext {

    private final ICamera camera;

    public RenderContextBase(ICamera camera) {
        this.camera = camera;
    }

    @Override
    public ICamera getCamera() {
        return this.camera;
    }
}

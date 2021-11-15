package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public class RenderContext {

    private final ICamera camera;

    public RenderContext(ICamera camera) {
        this.camera = camera;
    }

    public ICamera getCamera() {
        return this.camera;
    }

}

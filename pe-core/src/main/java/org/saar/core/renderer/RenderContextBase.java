package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public class RenderContextBase extends RenderContext {

    private final ICamera camera;
    private final RenderingHintsBase hints = new RenderingHintsBase();

    public RenderContextBase(ICamera camera) {
        this.camera = camera;
    }

    @Override
    public ICamera getCamera() {
        return this.camera;
    }

    @Override
    public RenderingHintsBase getHints() {
        return this.hints;
    }
}

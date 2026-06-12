package org.saar.core.renderer;

import org.saar.core.camera.ICamera;
import org.saar.lwjgl.opengl.clipplane.ClipPlane;

public class RenderContext {

    private final ICamera camera;
    private final ClipPlane clipPlane;

    public RenderContext(ICamera camera) {
        this(camera, null);
    }

    public RenderContext(ICamera camera, ClipPlane clipPlane) {
        this.camera = camera;
        this.clipPlane = clipPlane;
    }

    public ICamera getCamera() {
        return camera;
    }

    public ClipPlane getClipPlane() {
        return clipPlane;
    }
}

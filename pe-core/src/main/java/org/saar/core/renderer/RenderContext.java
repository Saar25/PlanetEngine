package org.saar.core.renderer;

import org.saar.core.camera.ICamera;
import org.saar.lwjgl.opengl.clipplane.ClipPlane;

public class RenderContext {

    private final ICamera camera;
    private final ClipPlane clipPlane;

    public RenderContext(ICamera camera, ClipPlane clipPlane) {
        this.camera = camera;
        this.clipPlane = clipPlane;
    }

    public RenderContext(ICamera camera) {
        this(camera, null);
    }

    public ICamera getCamera() {
        return this.camera;
    }

    public ClipPlane getClipPlane() {
        return this.clipPlane;
    }
}

package org.saar.core.renderer;

import org.saar.core.camera.ICamera;

public abstract class RenderContext {

    public abstract ICamera getCamera();

    public abstract RenderingHints getHints();

}

package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext

class DeferredRenderContext(context: RenderContext, val camera: ICamera) : RenderContext(context) {
    constructor(camera: ICamera) : this(RenderContext(), camera)
}
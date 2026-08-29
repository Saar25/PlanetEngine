package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext

class ForwardRenderContext(context: RenderContext, val camera: ICamera) : RenderContext(context) {
    constructor(camera: ICamera) : this(RenderContext(), camera)
}
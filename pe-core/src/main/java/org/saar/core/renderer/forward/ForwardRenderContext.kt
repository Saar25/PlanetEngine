package org.saar.core.renderer.forward

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext

class ForwardRenderContext(context: RenderContext, @JvmField val camera: ICamera) : RenderContext(context)
package org.saar.core.renderer.shadow

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext

class ShadowsRenderContext(context: RenderContext, @JvmField val camera: ICamera) : RenderContext(context)
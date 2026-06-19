package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext

class DeferredRenderContext(context: RenderContext, @JvmField val camera: ICamera) : RenderContext(context)
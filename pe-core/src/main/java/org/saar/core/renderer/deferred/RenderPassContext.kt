package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera

data class RenderPassContext(val camera: ICamera, val buffers: DeferredRenderingBuffers)

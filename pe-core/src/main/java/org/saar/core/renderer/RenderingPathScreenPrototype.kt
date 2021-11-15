package org.saar.core.renderer

import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.ScreenPrototype

interface RenderingPathScreenPrototype<T : RenderPassBuffers> : ScreenPrototype {
    val buffers: T
}
package org.saar.core.renderer.renderpass

import org.saar.core.renderer.RenderContext

interface RenderPass<in T : RenderPassBuffers> {

    fun prepare(context: RenderContext, buffers: T) = Unit

    fun render(context: RenderContext, buffers: T)

    fun delete()

}
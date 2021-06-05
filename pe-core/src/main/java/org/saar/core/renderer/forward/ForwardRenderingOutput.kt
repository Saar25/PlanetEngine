package org.saar.core.renderer.forward

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen

class ForwardRenderingOutput(private val screen: Screen,
                             private val buffers: ForwardRenderingBuffers) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.buffers.albedo

    fun toDepthTexture() = this.buffers.depth

    fun asPostProcessingInput() = PostProcessingBuffers(this.buffers.albedo, this.buffers.depth)

}
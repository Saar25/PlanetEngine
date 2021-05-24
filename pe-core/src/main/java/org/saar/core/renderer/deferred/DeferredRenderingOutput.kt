package org.saar.core.renderer.deferred

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen

class DeferredRenderingOutput(private val screen: Screen,
                              private val buffers: DeferredRenderingBuffers) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.buffers.albedo

    fun toDepthTexture() = this.buffers.depth

    fun asPostProcessingInput() = PostProcessingBuffers(this.buffers.albedo, this.buffers.depth)

}
package org.saar.core.renderer.pbr

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen

class PBRenderingOutput(private val screen: Screen,
                        private val buffers: PBRenderingBuffers) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.buffers.albedo

    fun toDepthTexture() = this.buffers.depth

    fun asPostProcessingInput() = PostProcessingBuffers(this.buffers.albedo, this.buffers.depth)

}
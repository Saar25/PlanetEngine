package org.saar.core.renderer.forward

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class ForwardRenderingOutput(private val screen: Screen,
                             private val colourTexture: ReadOnlyTexture,
                             private val depthTexture: ReadOnlyTexture) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.colourTexture

    fun toDepthTexture() = this.depthTexture

    fun asPostProcessingInput() = PostProcessingBuffers(this.colourTexture, this.depthTexture)

}
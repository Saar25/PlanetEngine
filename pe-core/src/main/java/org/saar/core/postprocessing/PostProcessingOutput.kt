package org.saar.core.postprocessing

import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.fbos.FboBlitFilter
import org.saar.lwjgl.opengl.fbos.ReadableFbo
import org.saar.lwjgl.opengl.utils.GlBuffer

class PostProcessingOutput(private val fbo: ReadableFbo) : RenderingOutput {

    override fun to(screen: Screen) {
        screen.setAsDraw()
        this.fbo.blitFramebuffer(
            0, 0, this.fbo.width, this.fbo.height,
            0, 0, screen.width, screen.height,
            FboBlitFilter.LINEAR, GlBuffer.COLOUR
        )
    }
}
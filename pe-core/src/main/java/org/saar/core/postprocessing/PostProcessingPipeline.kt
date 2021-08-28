package org.saar.core.postprocessing

import org.saar.core.camera.ICamera
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.FboBlitFilter
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlBuffer

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val prototype = PostProcessingScreenPrototype()

    private val screen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val colourTexture: ReadOnlyTexture
        get() = this.prototype.colourTexture

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    fun process(buffers: PostProcessingBuffers): PostProcessingOutput {
        return process(null, buffers)
    }

    @JvmOverloads
    fun process(camera: ICamera?, input: PostProcessingBuffers, screen: Screen = MainScreen): PostProcessingOutput {
        this.screen.assureSize(screen.width, screen.height)
        screen.copyTo(this.screen, FboBlitFilter.NEAREST,
            GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        this.screen.setAsDraw()

        val outputBuffers = runPipeline(input, camera)

        return PostProcessingOutput(this.screen, outputBuffers.colour, outputBuffers.depth)
    }

    private fun runPipeline(input: PostProcessingBuffers, camera: ICamera?): PostProcessingBuffers {
        return this.processors.fold(input) { buffers, processor ->
            StencilTest.apply(this.stencilState)
            DepthTest.disable()

            processor.process(PostProcessingContext(camera, buffers))

            PostProcessingBuffers(this.colourTexture, input.depth)
        }
    }

    fun delete() {
        this.processors.forEach { it.delete() }
        this.screen.delete()
    }

}
package org.saar.core.postprocessing

import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val screenPrototype = PostProcessingScreenPrototype()

    private val screen = Screens.fromPrototype(this.screenPrototype, Fbo.create(0, 0))

    private val colourTexture: ReadOnlyTexture
        get() = this.screenPrototype.colourTexture

    fun process(buffers: PostProcessingBuffers): PostProcessingOutput {
        this.screen.resizeToMainScreen()

        this.screen.setAsDraw()

        var currentBuffers = buffers
        for (processor in this.processors) {
            processor.process(currentBuffers)

            currentBuffers = PostProcessingBuffers(
                this.colourTexture, currentBuffers.depth)
        }

        return PostProcessingOutput(this.screen, currentBuffers.colour, buffers.depth)
    }

    fun delete() {
        this.processors.forEach { it.delete() }
        this.screen.delete()
    }

}
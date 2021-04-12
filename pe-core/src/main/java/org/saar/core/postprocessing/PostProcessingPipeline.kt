package org.saar.core.postprocessing

import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val screenPrototype = PostProcessingScreenPrototype()

    private val screen = Screens.fromPrototype(this.screenPrototype, Fbo.create(0, 0))

    fun process(texture: ReadOnlyTexture): PostProcessingOutput {
        this.screen.resizeToMainScreen()

        var lastTexture = texture
        this.screen.setAsDraw()
        for (processor in this.processors) {
            processor.process(lastTexture)
            lastTexture = this.screenPrototype.colourTexture
        }

        return PostProcessingOutput(this.screen, lastTexture)
    }

    fun delete() {
        this.processors.forEach { it.delete() }
        this.screen.delete()
    }

}
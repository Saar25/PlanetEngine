package org.saar.core.postprocessing

import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val screenPrototype = PostProcessingScreenPrototype()

    private val screen = Screens.fromPrototype(this.screenPrototype, Fbo.create(0, 0))

    fun delete() {
        this.processors.forEach { it.delete() }
        this.screen.delete()
    }

}
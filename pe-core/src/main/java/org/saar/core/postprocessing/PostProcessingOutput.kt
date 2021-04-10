package org.saar.core.postprocessing

import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen

class PostProcessingOutput(private val screen: OffScreen) : RenderingOutput {

    override fun to(screen: Screen) {
        screen.setAsDraw()
        this.screen.copyTo(screen)
    }
}
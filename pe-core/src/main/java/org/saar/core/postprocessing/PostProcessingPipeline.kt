package org.saar.core.postprocessing

import org.saar.core.screen.Screen
import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.fbos.Fbo

class PostProcessingPipeline(private vararg val processors: PostProcessor) {

    private val screenPrototype: ScreenPrototype = PostProcessingScreenPrototype()

    private val screen: Screen = Screens.fromPrototype(this.screenPrototype, Fbo.create(0, 0))



}
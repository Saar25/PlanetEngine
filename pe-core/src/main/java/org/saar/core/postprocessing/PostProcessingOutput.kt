package org.saar.core.postprocessing

import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class PostProcessingOutput(private val screen: OffScreen, private val texture: ReadOnlyTexture) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.texture
}
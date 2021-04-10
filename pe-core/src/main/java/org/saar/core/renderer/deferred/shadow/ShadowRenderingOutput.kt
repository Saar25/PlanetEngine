package org.saar.core.renderer.deferred.shadow

import org.saar.core.renderer.RenderingOutput
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class ShadowRenderingOutput(private val screen: Screen, private val depthTexture: ReadOnlyTexture) : RenderingOutput {

    override fun to(screen: Screen) = this.screen.copyTo(screen)

    override fun toTexture() = this.depthTexture

}
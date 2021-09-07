package org.saar.core.renderer

import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class RenderingOutput<T : RenderPassBuffers>(
    private val screen: Screen,
    val buffers: T) {

    fun to(screen: Screen) = this.screen.copyTo(screen)

    fun toTexture(): ReadOnlyTexture = this.buffers.albedo

    fun toMainScreen() = to(MainScreen)
}
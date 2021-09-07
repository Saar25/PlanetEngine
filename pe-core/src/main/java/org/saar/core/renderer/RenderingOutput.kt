package org.saar.core.renderer

import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen

class RenderingOutput<T : RenderPassBuffers>(
    private val screen: Screen,
    val buffers: T) {

    fun to(screen: Screen) = this.screen.copyTo(screen)

    fun toMainScreen() = to(MainScreen)
}
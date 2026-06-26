package org.saar.core.screen

import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.fbo.WindowFbo

object MainScreen : FboScreen() {

    override val width get() = Window.current()!!.width

    override val height get() = Window.current()!!.height

    override val fbo: WindowFbo = WindowFbo.getInstance()
}
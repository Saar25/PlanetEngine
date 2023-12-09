package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.WindowFbo

object MainScreen : FboScreen() {

    override val fbo: WindowFbo = WindowFbo.getInstance()
}
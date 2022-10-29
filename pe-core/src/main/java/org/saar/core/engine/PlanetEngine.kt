package org.saar.core.engine

import org.lwjgl.glfw.GLFW
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils
import kotlin.concurrent.thread

class PlanetEngine : Engine {

    override fun run(application: Application) {
        thread {
            val window = Window.create("Lwjgl", 1200, 700, true)

            application.initialize(window)

            val keyboard = window.keyboard
            while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
                application.update(window)

                GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)
                application.render(window)

                window.swapBuffers()
                window.pollEvents()
            }

            application.close(window)
            window.destroy()
        }
    }
}
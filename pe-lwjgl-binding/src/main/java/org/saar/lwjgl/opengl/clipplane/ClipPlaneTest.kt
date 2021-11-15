package org.saar.lwjgl.opengl.clipplane

import org.lwjgl.opengl.GL11

object ClipPlaneTest {

    private var enabled: Array<Boolean> = Array(6) { false }

    @JvmStatic
    fun enable(index: Int) {
        if (!this.enabled[index]) {
            GL11.glEnable(GL11.GL_CLIP_PLANE0 + index)
            this.enabled[index] = true
        }
    }

    @JvmStatic
    fun disable(index: Int) {
        if (this.enabled[index]) {
            GL11.glDisable(GL11.GL_CLIP_PLANE0 + index)
            this.enabled[index] = true
        }
    }

    @JvmStatic
    fun disable() = (0 until 6).forEach { disable(it) }
}
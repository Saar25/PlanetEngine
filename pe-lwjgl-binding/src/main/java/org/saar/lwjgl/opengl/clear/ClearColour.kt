package org.saar.lwjgl.opengl.clear

import org.joml.component1
import org.joml.component2
import org.joml.component3
import org.joml.component4
import org.lwjgl.opengl.GL11
import org.saar.maths.utils.Vector4

object ClearColour {

    private val value = Vector4.of(0f, 0f, 0f, 0f)

    @JvmStatic
    fun set(r: Float, g: Float, b: Float) = set(r, g, b, 0f)

    @JvmStatic
    fun set(r: Float, g: Float, b: Float, a: Float) {
        if (!this.value.equals(r, g, b, a)) {
            this.value.set(r, g, b, a)

            setColour()
        }
    }

    private fun setColour() {
        val (r, g, b, a) = this.value
        GL11.glClearColor(r, g, b, a)
    }
}

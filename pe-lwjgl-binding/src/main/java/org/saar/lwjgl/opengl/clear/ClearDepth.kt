package org.saar.lwjgl.opengl.clear

import org.lwjgl.opengl.GL11

object ClearDepth {

    private var value = 1.0

    fun set(value: Float) = set(value.toDouble())

    @JvmStatic
    fun set(value: Double) {
        if (this.value != value) {
            this.value = value

            setDepth()
        }
    }

    private fun setDepth() {
        GL11.glClearDepth(this.value)
    }
}
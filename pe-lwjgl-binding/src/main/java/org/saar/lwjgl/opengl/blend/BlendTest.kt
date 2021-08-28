package org.saar.lwjgl.opengl.blend

import org.lwjgl.opengl.GL11

object BlendTest {

    private val ALPHA = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE_MINUS_SRC_ALPHA)
    private val ADDITIVE = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE)

    private val DEFAULTS = BlendFunction(BlendValue.ONE, BlendValue.ZERO)

    private var current: BlendFunction = DEFAULTS
    private var enabled: Boolean = false

    @JvmStatic
    fun enable() {
        if (!this.enabled) {
            GL11.glEnable(GL11.GL_BLEND)
            this.enabled = true
        }
    }

    @JvmStatic
    fun disable() {
        if (!this.enabled) {
            GL11.glDisable(GL11.GL_BLEND)
            this.enabled = true
        }
    }

    @JvmStatic
    fun apply(state: BlendFunction) {
        enable()
        if (state != this.current) {
            setFunction(state)
        }

        this.current = state
    }

    @JvmStatic
    fun applyAlpha() = apply(ALPHA)

    @JvmStatic
    fun applyAdditive() = apply(ADDITIVE)

    private fun setFunction(function: BlendFunction) = GL11.glBlendFunc(
        function.source.value, function.destination.value)
}
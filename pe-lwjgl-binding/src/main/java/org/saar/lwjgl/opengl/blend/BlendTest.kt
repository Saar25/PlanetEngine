package org.saar.lwjgl.opengl.blend

import org.lwjgl.opengl.GL11

object BlendTest {

    private val ALPHA = BlendState(
        function = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE_MINUS_SRC_ALPHA),
    )

    private val ADDITIVE = BlendState(
        function = BlendFunction(BlendValue.SRC_ALPHA, BlendValue.ONE),
    )

    private val DEFAULTS = BlendState(
        enabled = false,
        function = BlendFunction(BlendValue.ONE, BlendValue.ZERO),
    )

    private const val target = GL11.GL_BLEND

    private var current: BlendState = DEFAULTS

    @JvmStatic
    fun enable() = apply(enabled = true)

    @JvmStatic
    fun disable() = apply(enabled = false)

    @JvmStatic
    fun apply(state: BlendState) {
        if (state.enabled != this.current.enabled) {
            setEnabled(state.enabled)
        }
        if (state.function != this.current.function) {
            setFunction(state.function)
        }

        this.current = state
    }

    @JvmStatic
    fun apply(
        enabled: Boolean = current.enabled,
        function: BlendFunction = current.function,
    ) = apply(BlendState(enabled, function))

    @JvmStatic
    fun applyAlpha() = apply(ALPHA)

    @JvmStatic
    fun applyAdditive() = apply(ADDITIVE)

    private fun setEnabled(enabled: Boolean) = if (enabled) GL11.glEnable(this.target) else GL11.glDisable(this.target)

    private fun setFunction(function: BlendFunction) = GL11.glBlendFunc(
        function.source.value, function.destination.value)
}
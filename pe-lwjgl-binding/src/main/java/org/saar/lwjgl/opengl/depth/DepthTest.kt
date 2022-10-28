package org.saar.lwjgl.opengl.depth

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Comparator

object DepthTest {

    private val DEFAULTS = DepthState(
        enabled = false,
        function = DepthFunction(Comparator.LESS),
        mask = DepthMask.WRITE
    )

    private const val target = GL11.GL_DEPTH_TEST

    private var current: DepthState = DEFAULTS

    @JvmStatic
    fun enable() = apply(enabled = true)

    @JvmStatic
    fun disable() = apply(enabled = false)

    @JvmStatic
    fun apply(state: DepthState) {
        if (state.enabled != current.enabled) {
            setEnabled(state.enabled)
        }
        if (state.function != current.function) {
            setFunction(state.function)
        }
        if (state.mask != current.mask) {
            setMask(state.mask)
        }

        this.current = state
    }

    @JvmStatic
    fun apply(
        enabled: Boolean = current.enabled,
        function: DepthFunction = current.function,
        mask: DepthMask = current.mask,
    ) = apply(DepthState(enabled, function, mask))

    private fun setEnabled(enabled: Boolean) = if (enabled) GL11.glEnable(this.target) else GL11.glDisable(this.target)

    private fun setFunction(function: DepthFunction) = GL11.glDepthFunc(function.comparator.value)

    private fun setMask(mask: DepthMask) = GL11.glDepthMask(mask.value)
}
package org.saar.lwjgl.opengl.depth

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Comparator

object Depth {

    private val DEFAULTS = DepthState(
        DepthFunction(Comparator.LESS),
        DepthMask.WRITE
    )

    private var current: DepthState = DEFAULTS

    @JvmStatic
    fun disable() {
        GL11.glDisable(GL11.GL_DEPTH_TEST)
    }

    @JvmStatic
    fun enable() {
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        setFunction(current.function)
        setMask(current.mask)
    }

    @JvmStatic
    fun apply(state: DepthState) {
        if (state.function != current.function) {
            setFunction(state.function)
        }
        if (state.mask != current.mask) {
            setMask(state.mask)
        }

        this.current = state
    }

    @JvmStatic
    fun apply(function: DepthFunction = current.function,
              mask: DepthMask = current.mask) {
        apply(DepthState(function, mask))
    }

    private fun setFunction(function: DepthFunction) = GL11.glDepthFunc(function.comparator.value)

    private fun setMask(mask: DepthMask) = GL11.glDepthMask(mask.value)
}
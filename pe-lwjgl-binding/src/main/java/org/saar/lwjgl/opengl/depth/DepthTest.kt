package org.saar.lwjgl.opengl.depth

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Comparator

object DepthTest {

    private val DEFAULTS = DepthState(
        DepthFunction(Comparator.LESS),
        DepthMask.WRITE
    )

    private var current: DepthState = DEFAULTS
    private var enabled: Boolean = false

    @JvmStatic
    fun enable() {
        if (!this.enabled) {
            GL11.glEnable(GL11.GL_DEPTH_TEST)
            this.enabled = true
        }
    }

    @JvmStatic
    fun disable() {
        if (this.enabled) {
            GL11.glDisable(GL11.GL_DEPTH_TEST)
            this.enabled = false
        }
    }

    @JvmStatic
    fun apply(state: DepthState) {
        enable()
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
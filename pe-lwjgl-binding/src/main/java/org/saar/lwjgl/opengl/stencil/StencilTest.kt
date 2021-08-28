package org.saar.lwjgl.opengl.stencil

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Comparator

object StencilTest {

    private val DEFAULTS = StencilState(
        StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.KEEP),
        StencilFunction(Comparator.ALWAYS, 0, 1),
        StencilMask.UNCHANGED
    )

    private var current: StencilState = DEFAULTS
    private var enabled: Boolean = false

    @JvmStatic
    fun enable() {
        if (!this.enabled) {
            GL11.glEnable(GL11.GL_STENCIL_TEST)
            this.enabled = true
        }
    }

    @JvmStatic
    fun disable() {
        if (this.enabled) {
            GL11.glDisable(GL11.GL_STENCIL_TEST)
            this.enabled = false
        }
    }

    @JvmStatic
    fun apply(state: StencilState) {
        enable()
        if (state.function != current.function) {
            setFunction(state.function)
        }
        if (state.operation != current.operation) {
            setOperation(state.operation)
        }
        if (state.mask != current.mask) {
            setMask(state.mask)
        }

        this.current = state
    }

    @JvmStatic
    fun apply(operation: StencilOperation = current.operation,
              function: StencilFunction = current.function,
              mask: StencilMask = current.mask) {
        apply(StencilState(operation, function, mask))
    }

    private fun setFunction(function: StencilFunction) = GL11.glStencilFunc(
        function.comparator.value, function.reference, function.mask)

    private fun setOperation(operation: StencilOperation) = GL11.glStencilOp(
        operation.sFail.value, operation.dFail.value, operation.pass.value)

    private fun setMask(mask: StencilMask) = GL11.glStencilMask(mask.value)
}
package org.saar.lwjgl.opengl.stencil

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Comparator

object StencilTest {

    private val DEFAULTS = StencilState(false,
        StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.KEEP),
        StencilFunction(Comparator.ALWAYS, 0, 1),
        StencilMask.UNCHANGED
    )

    private const val target = GL11.GL_STENCIL_TEST

    private var current: StencilState = DEFAULTS

    @JvmStatic
    fun enable() = apply(enabled = true)

    @JvmStatic
    fun disable() = apply(enabled = false)

    @JvmStatic
    fun apply(state: StencilState) {
        if (state.enabled != current.enabled) {
            setEnabled(state.enabled)
        }
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
    fun apply(
        enabled: Boolean = current.enabled,
        operation: StencilOperation = current.operation,
        function: StencilFunction = current.function,
        mask: StencilMask = current.mask,
    ) = apply(StencilState(enabled, operation, function, mask))

    private fun setEnabled(enabled: Boolean) = if (enabled) GL11.glEnable(this.target) else GL11.glDisable(this.target)

    private fun setFunction(function: StencilFunction) = GL11.glStencilFunc(
        function.comparator.value, function.reference, function.mask)

    private fun setOperation(operation: StencilOperation) = GL11.glStencilOp(
        operation.sFail.value, operation.dFail.value, operation.pass.value)

    private fun setMask(mask: StencilMask) = GL11.glStencilMask(mask.value)
}
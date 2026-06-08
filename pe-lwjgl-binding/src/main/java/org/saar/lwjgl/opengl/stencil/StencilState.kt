package org.saar.lwjgl.opengl.stencil

import org.saar.lwjgl.opengl.constants.Comparator

data class StencilState(
    val enabled: Boolean,
    val operation: StencilOperation,
    val function: StencilFunction,
    val mask: StencilMask,
) {
    constructor(operation: StencilOperation, function: StencilFunction, mask: StencilMask) :
            this(true, operation, function, mask)

    companion object {
        val DEFAULTS = StencilState(
            false,
            StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.KEEP),
            StencilFunction(Comparator.ALWAYS, 0, 1),
            StencilMask.UNCHANGED
        )

        val ALWAYS_WRITE = StencilState(
            StencilOperation.REPLACE_ON_PASS,
            StencilFunction(Comparator.ALWAYS, 1, 0xFF),
            StencilMask.UNCHANGED,
        )

        val UNWRITTEN_ONLY = StencilState(
            StencilOperation.ALWAYS_KEEP,
            StencilFunction(Comparator.EQUAL, 0),
            StencilMask.UNCHANGED,
        )

        val REPLACE = StencilState(
            StencilOperation.ALWAYS_KEEP,
            StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF),
            StencilMask.UNCHANGED
        )
    }
}
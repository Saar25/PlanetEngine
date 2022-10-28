package org.saar.lwjgl.opengl.stencil

data class StencilState(
    val enabled: Boolean,
    val operation: StencilOperation,
    val function: StencilFunction,
    val mask: StencilMask,
) {
    constructor(operation: StencilOperation, function: StencilFunction, mask: StencilMask) :
            this(true, operation, function, mask)
}
package org.saar.lwjgl.opengl.stencil

data class StencilState(
    val operation: StencilOperation,
    val function: StencilFunction,
    val mask: StencilMask,
)
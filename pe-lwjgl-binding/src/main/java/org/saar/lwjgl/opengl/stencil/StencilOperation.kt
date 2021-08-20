package org.saar.lwjgl.opengl.stencil

data class StencilOperation(
    val sFail: StencilValue,
    val dFail: StencilValue,
    val pass: StencilValue
)
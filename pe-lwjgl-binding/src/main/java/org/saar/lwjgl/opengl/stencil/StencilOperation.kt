package org.saar.lwjgl.opengl.stencil

data class StencilOperation(
    val sFail: StencilValue,
    val dFail: StencilValue,
    val pass: StencilValue
) {
    companion object {
        @JvmField
        val ALWAYS_KEEP = StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.KEEP)

        @JvmField
        val REPLACE_ON_PASS = StencilOperation(StencilValue.KEEP, StencilValue.KEEP, StencilValue.REPLACE)
    }
}
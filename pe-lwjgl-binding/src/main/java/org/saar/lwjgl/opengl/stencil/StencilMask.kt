package org.saar.lwjgl.opengl.stencil

data class StencilMask(val value: Int) {

    companion object {
        @JvmField
        val ZERO = StencilMask(0)

        @JvmField
        val UNCHANGED = StencilMask(-1)
    }
}
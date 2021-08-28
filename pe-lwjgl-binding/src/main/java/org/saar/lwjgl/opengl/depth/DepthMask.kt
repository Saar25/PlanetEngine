package org.saar.lwjgl.opengl.depth

data class DepthMask(val value: Boolean) {

    companion object {
        @JvmField
        val READ = DepthMask(false)

        @JvmField
        val WRITE = DepthMask(true)
    }
}
package org.saar.lwjgl.opengl.depth

data class DepthState(
    val function: DepthFunction,
    val mask: DepthMask,
)
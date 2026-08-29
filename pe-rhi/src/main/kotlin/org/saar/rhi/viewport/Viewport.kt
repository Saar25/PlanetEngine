package org.saar.rhi.viewport

data class Viewport(
    val x: Float = 0f,
    val y: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
    val minDepth: Float? = null,
    val maxDepth: Float? = null,
)

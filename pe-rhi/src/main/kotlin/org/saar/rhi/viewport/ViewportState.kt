package org.saar.rhi.viewport

fun ViewportState(viewport: Viewport) = ViewportState(viewports = listOf(viewport))

data class ViewportState(
    val viewports: List<Viewport> = emptyList(),
    val scissors: List<Scissor> = emptyList(),
)

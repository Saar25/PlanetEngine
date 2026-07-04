package org.saar.rhi.resterization

data class RasterizationState(
    val cullMode: CullMode,
    val frontFace: FrontFace,
    val polygonMode: PolygonMode,
    val lineWidth: Float,
)
package org.saar.rhi.resterization

data class RasterizationState(
    val cullMode: CullMode? = null,
    val frontFace: FrontFace? = null,
    val polygonMode: PolygonMode? = null,
    val lineWidth: Float? = null,
)
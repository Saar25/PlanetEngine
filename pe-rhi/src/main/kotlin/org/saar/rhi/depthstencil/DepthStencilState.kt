package org.saar.rhi.depthstencil

data class DepthStencilState(
    val depthTestEnable: Boolean? = null,
    val depthWriteEnable: Boolean? = null,
    val depthCompareOp: CompareOp? = null,
    val depthBoundsTestEnable: Boolean? = null,
    val stencilTestEnable: Boolean? = null,
    val front: StencilOpState? = null,
    val back: StencilOpState? = null,
    val minDepthBounds: Float? = null,
    val maxDepthBounds: Float? = null,
)

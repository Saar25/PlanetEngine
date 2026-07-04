package org.saar.rhi.depthstencil

fun DepthStencilState(
    depthTestEnable: Boolean? = null,
    depthWriteEnable: Boolean? = null,
    depthCompareOp: CompareOp? = null,
    depthBoundsTestEnable: Boolean? = null,
    stencilTestEnable: Boolean? = null,
    stencil: StencilOpState? = null,
    minDepthBounds: Float? = null,
    maxDepthBounds: Float? = null,
) = DepthStencilState(
    depthTestEnable = depthTestEnable,
    depthWriteEnable = depthWriteEnable,
    depthCompareOp = depthCompareOp,
    depthBoundsTestEnable = depthBoundsTestEnable,
    stencilTestEnable = stencilTestEnable,
    front = stencil,
    back = stencil,
    minDepthBounds = minDepthBounds,
    maxDepthBounds = maxDepthBounds,
)

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

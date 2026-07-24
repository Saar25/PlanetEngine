package org.saar.rhi.renderpass

fun RenderPass(
    colorAttachment: RenderPassAttachment,
    depthAttachment: RenderPassAttachment? = null,
) = RenderPass(
    colorAttachments = listOf(colorAttachment),
    depthAttachment = depthAttachment,
)

data class RenderPass(
    val colorAttachments: List<RenderPassAttachment> = emptyList(),
    val depthAttachment: RenderPassAttachment? = null,
)

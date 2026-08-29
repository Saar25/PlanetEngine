package org.saar.rhi.vulkan.rasterization

import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.FrontFace
import org.saar.rhi.rasterization.PolygonMode
import org.saar.rhi.rasterization.RasterizationState

fun RasterizationState.toVulkan() =
    VkPipelineRasterizationStateCreateInfo.calloc().apply {
        `sType$Default`()
        polygonMode?.let { polygonMode(it.vkValue) }
        cullMode?.let { cullMode(it.vkValue) }
        frontFace?.let { frontFace(it.vkValue) }
        lineWidth?.let { lineWidth(it) }
    }


private val PolygonMode.vkValue
    get() = when (this) {
        PolygonMode.POINT -> VK10.VK_POLYGON_MODE_POINT
        PolygonMode.LINE -> VK10.VK_POLYGON_MODE_LINE
        PolygonMode.FILL -> VK10.VK_POLYGON_MODE_FILL
    }

private val CullMode.vkValue
    get() = when (this) {
        CullMode.NONE -> VK10.VK_CULL_MODE_NONE
        CullMode.FRONT -> VK10.VK_CULL_MODE_FRONT_BIT
        CullMode.BACK -> VK10.VK_CULL_MODE_BACK_BIT
        CullMode.FRONT_AND_BACK -> VK10.VK_CULL_MODE_FRONT_AND_BACK
    }

private val FrontFace.vkValue
    get() = when (this) {
        FrontFace.COUNTER_CLOCKWISE -> VK10.VK_FRONT_FACE_COUNTER_CLOCKWISE
        FrontFace.CLOCKWISE -> VK10.VK_FRONT_FACE_CLOCKWISE
    }
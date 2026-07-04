package org.saar.rhi.vulkan.resterization

import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.FrontFace
import org.saar.rhi.resterization.PolygonMode
import org.saar.rhi.resterization.RasterizationState

fun RasterizationState.toVulkan() =
    VkPipelineRasterizationStateCreateInfo.calloc().also {
        it.`sType$Default`()
        it.polygonMode(this.polygonMode.vkValue)
        it.cullMode(this.cullMode.vkValue)
        it.frontFace(this.frontFace.vkValue)
        it.lineWidth(this.lineWidth)
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
        CullMode.FRONT_BIT -> VK10.VK_CULL_MODE_FRONT_BIT
        CullMode.BACK_BIT -> VK10.VK_CULL_MODE_BACK_BIT
        CullMode.FRONT_AND_BACK -> VK10.VK_CULL_MODE_FRONT_AND_BACK
    }

private val FrontFace.vkValue
    get() = when (this) {
        FrontFace.COUNTER_CLOCKWISE -> VK10.VK_FRONT_FACE_COUNTER_CLOCKWISE
        FrontFace.CLOCKWISE -> VK10.VK_FRONT_FACE_CLOCKWISE
    }
package org.saar.rhi.vulkan.inputassembly

import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo
import org.saar.rhi.inputassembly.InputAssemblyState
import org.saar.rhi.inputassembly.PrimitiveTopology

fun InputAssemblyState.toVulkan() =
    VkPipelineInputAssemblyStateCreateInfo.calloc().apply {
        `sType$Default`()
        topology?.vkValue?.let { topology(it) }
        primitiveRestartEnable?.let { primitiveRestartEnable(it) }
    }

private val PrimitiveTopology.vkValue
    get() = when (this) {
        PrimitiveTopology.POINT_LIST -> VK10.VK_PRIMITIVE_TOPOLOGY_POINT_LIST
        PrimitiveTopology.LINE_LIST -> VK10.VK_PRIMITIVE_TOPOLOGY_LINE_LIST
        PrimitiveTopology.LINE_STRIP -> VK10.VK_PRIMITIVE_TOPOLOGY_LINE_STRIP
        PrimitiveTopology.TRIANGLE_LIST -> VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST
        PrimitiveTopology.TRIANGLE_STRIP -> VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_STRIP
        PrimitiveTopology.TRIANGLE_FAN -> VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_FAN
        PrimitiveTopology.LINE_LIST_ADJACENCY -> VK10.VK_PRIMITIVE_TOPOLOGY_LINE_LIST_WITH_ADJACENCY
        PrimitiveTopology.LINE_STRIP_ADJACENCY -> VK10.VK_PRIMITIVE_TOPOLOGY_LINE_STRIP_WITH_ADJACENCY
        PrimitiveTopology.TRIANGLE_LIST_ADJACENCY -> VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST_WITH_ADJACENCY
        PrimitiveTopology.TRIANGLE_STRIP_ADJACENCY -> VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_STRIP_WITH_ADJACENCY
        PrimitiveTopology.PATCH_LIST -> VK10.VK_PRIMITIVE_TOPOLOGY_PATCH_LIST
    }

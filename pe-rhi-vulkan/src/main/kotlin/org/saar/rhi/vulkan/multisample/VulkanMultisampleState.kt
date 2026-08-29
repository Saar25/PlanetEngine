package org.saar.rhi.vulkan.multisample

import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo
import org.saar.rhi.multisample.MultisampleState

fun MultisampleState.toVulkan() =
    VkPipelineMultisampleStateCreateInfo.calloc().apply {
        `sType$Default`()
        this@toVulkan.rasterizationSamplesBits?.let { rasterizationSamples(if (it == 0) 0 else 1 shl (it - 1)) }
        this@toVulkan.sampleShadingEnable?.let { sampleShadingEnable(it) }
        this@toVulkan.minSampleShading?.let { minSampleShading(it) }
        // this@toVulkan.sampleMask?.let { pSampleMask(sampleMask(intArrayOf(it))) }
        this@toVulkan.alphaToCoverageEnable?.let { alphaToCoverageEnable(it) }
        this@toVulkan.alphaToOneEnable?.let { alphaToOneEnable(it) }
    }

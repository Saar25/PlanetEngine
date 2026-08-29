package org.saar.rhi.vulkan.shader

import org.lwjgl.system.MemoryUtil
import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkAllocationCallbacks
import org.lwjgl.vulkan.VkDevice
import org.lwjgl.vulkan.VkShaderModuleCreateInfo
import org.saar.rhi.shader.ShaderModule
import org.saar.rhi.vulkan.result.translateVulkanResult

fun ShaderModule.toVulkan(device: VkDevice, allocator: VkAllocationCallbacks? = null): Long {
    val createInfo = VkShaderModuleCreateInfo.calloc().apply {
        `sType$Default`()
        pCode(this@toVulkan.code)
    }

    val pShaderModule = MemoryUtil.memAllocLong(1)
    val err = VK10.vkCreateShaderModule(device, createInfo, allocator, pShaderModule)
    val shaderModule = pShaderModule.get(0)

    MemoryUtil.memFree(pShaderModule)
    if (err != VK10.VK_SUCCESS) {
        throw AssertionError("Failed to create shader module: " + translateVulkanResult(err))
    }

    return shaderModule
}
package org.saar.rhi.vulkan.renderpass

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.*
import org.saar.rhi.renderpass.LoadOp
import org.saar.rhi.renderpass.RenderPass
import org.saar.rhi.renderpass.StoreOp
import org.saar.rhi.vulkan.texture.vkValue

fun RenderPass.toVulkan(device: VkDevice) = VulkanRenderPass(this, device)

class VulkanRenderPass(
    private val renderPass: RenderPass,
    private val device: VkDevice,
) {

    val handle: Long = createRenderPass()

    private var commandBuffer: VkCommandBuffer? = null
    private var framebuffer: Long = 0L
    private var width: Int = 0
    private var height: Int = 0

    fun configure(commandBuffer: VkCommandBuffer, framebuffer: Long, width: Int, height: Int) {
        this.commandBuffer = commandBuffer
        this.framebuffer = framebuffer
        this.width = width
        this.height = height
    }

    fun configure(commandBufferHandle: Long, framebuffer: Long, width: Int, height: Int) {
        this.commandBuffer = VkCommandBuffer(commandBufferHandle, this.device)
        this.framebuffer = framebuffer
        this.width = width
        this.height = height
    }

    fun begin() {
        val cmd = this.commandBuffer
            ?: throw IllegalStateException("Call configure() before begin()")

        MemoryStack.stackPush().use { stack ->
            val clearValues = createClearValues(stack)

            val renderArea = VkRect2D.calloc(stack)
            renderArea.offset(VkOffset2D.calloc(stack).set(0, 0))
            renderArea.extent(VkExtent2D.calloc(stack).set(this.width, this.height))

            val beginInfo = VkRenderPassBeginInfo.calloc(stack)
            beginInfo.`sType$Default`()
            beginInfo.renderPass(this@VulkanRenderPass.handle)
            beginInfo.framebuffer(this@VulkanRenderPass.framebuffer)
            beginInfo.renderArea(renderArea)
            beginInfo.pClearValues(clearValues)

            VK10.vkCmdBeginRenderPass(cmd, beginInfo, VK10.VK_SUBPASS_CONTENTS_INLINE)
        }
    }

    fun end() {
        val cmd = this.commandBuffer
            ?: throw IllegalStateException("Call configure() before end()")
        VK10.vkCmdEndRenderPass(cmd)
    }

    fun close() {
        VK10.vkDestroyRenderPass(this.device, this.handle, null)
    }

    private fun createRenderPass(): Long = MemoryStack.stackPush().use { stack ->
        val allAttachments = buildAttachmentDescriptions(stack)
        val colorAttachments = this.renderPass.colorAttachments
        val hasDepth = this.renderPass.depthAttachment != null

        val colorAttachmentRefs = VkAttachmentReference.calloc(colorAttachments.size, stack)
        for (i in colorAttachments.indices) {
            colorAttachmentRefs[i].set(i, VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)
        }

        val depthAttachmentRef = if (hasDepth) {
            VkAttachmentReference.calloc(stack).set(
                colorAttachments.size,
                VK10.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL,
            )
        } else null

        val subpass = VkSubpassDescription.calloc(1, stack)
        subpass.pipelineBindPoint(VK10.VK_PIPELINE_BIND_POINT_GRAPHICS)
        subpass.pColorAttachments(colorAttachmentRefs)
        if (depthAttachmentRef != null) {
            subpass.pDepthStencilAttachment(depthAttachmentRef)
        }

        val dependency = VkSubpassDependency.calloc(1, stack)
        dependency.srcSubpass(VK10.VK_SUBPASS_EXTERNAL)
        dependency.dstSubpass(0)
        dependency.srcStageMask(
            VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT or
                    VK10.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT,
        )
        dependency.dstStageMask(
            VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT or
                    VK10.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT,
        )
        dependency.srcAccessMask(0)
        dependency.dstAccessMask(
            VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT or
                    VK10.VK_ACCESS_DEPTH_STENCIL_ATTACHMENT_WRITE_BIT,
        )

        val createInfo = VkRenderPassCreateInfo.calloc(stack)
        createInfo.`sType$Default`()
        createInfo.pAttachments(allAttachments)
        createInfo.pSubpasses(subpass)
        createInfo.pDependencies(dependency)

        val pRenderPass = stack.mallocLong(1)
        val err = VK10.vkCreateRenderPass(this.device, createInfo, null, pRenderPass)
        if (err != VK10.VK_SUCCESS) {
            throw RuntimeException("Failed to create render pass: $err")
        }
        pRenderPass[0]
    }

    private fun buildAttachmentDescriptions(stack: MemoryStack): VkAttachmentDescription.Buffer {
        val totalAttachments =
            this.renderPass.colorAttachments.size + if (this.renderPass.depthAttachment != null) 1 else 0
        val descriptions = VkAttachmentDescription.calloc(totalAttachments, stack)

        for ((i, attachment) in this.renderPass.colorAttachments.withIndex()) {
            descriptions[i].format(attachment.format.vkValue)
            descriptions[i].samples(attachment.samples)
            descriptions[i].loadOp(attachment.loadOp.vkValue)
            descriptions[i].storeOp(attachment.storeOp.vkValue)
            descriptions[i].stencilLoadOp(attachment.stencilLoadOp.vkValue)
            descriptions[i].stencilStoreOp(attachment.stencilStoreOp.vkValue)
            descriptions[i].initialLayout(attachment.initialLayout ?: VK10.VK_IMAGE_LAYOUT_UNDEFINED)
            descriptions[i].finalLayout(
                attachment.finalLayout ?: KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR,
            )
        }

        this.renderPass.depthAttachment?.let { attachment ->
            descriptions[totalAttachments - 1].format(attachment.format.vkValue)
            descriptions[totalAttachments - 1].samples(attachment.samples)
            descriptions[totalAttachments - 1].loadOp(attachment.loadOp.vkValue)
            descriptions[totalAttachments - 1].storeOp(attachment.storeOp.vkValue)
            descriptions[totalAttachments - 1].stencilLoadOp(attachment.stencilLoadOp.vkValue)
            descriptions[totalAttachments - 1].stencilStoreOp(attachment.stencilStoreOp.vkValue)
            descriptions[totalAttachments - 1].initialLayout(
                attachment.initialLayout ?: VK10.VK_IMAGE_LAYOUT_UNDEFINED,
            )
            descriptions[totalAttachments - 1].finalLayout(
                attachment.finalLayout ?: VK10.VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL,
            )
        }

        return descriptions
    }

    private fun createClearValues(stack: MemoryStack): VkClearValue.Buffer {
        val totalAttachments =
            this.renderPass.colorAttachments.size + if (this.renderPass.depthAttachment != null) 1 else 0
        val clearValues = VkClearValue.calloc(totalAttachments, stack)

        for ((i, attachment) in this.renderPass.colorAttachments.withIndex()) {
            if (attachment.loadOp == LoadOp.CLEAR) {
                val c = attachment.clearColor
                clearValues[i].color().float32(0, c.r)
                clearValues[i].color().float32(1, c.g)
                clearValues[i].color().float32(2, c.b)
                clearValues[i].color().float32(3, c.a)
            }
        }

        this.renderPass.depthAttachment?.let { attachment ->
            val idx = totalAttachments - 1
            if (attachment.loadOp == LoadOp.CLEAR || attachment.stencilLoadOp == LoadOp.CLEAR) {
                clearValues[idx].depthStencil().depth(attachment.clearColor.depth)
                clearValues[idx].depthStencil().stencil(attachment.clearColor.stencil)
            }
        }

        return clearValues
    }
}

private val LoadOp.vkValue: Int
    get() = when (this) {
        LoadOp.LOAD -> VK10.VK_ATTACHMENT_LOAD_OP_LOAD
        LoadOp.CLEAR -> VK10.VK_ATTACHMENT_LOAD_OP_CLEAR
        LoadOp.DONT_CARE -> VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE
    }

private val StoreOp.vkValue: Int
    get() = when (this) {
        StoreOp.STORE -> VK10.VK_ATTACHMENT_STORE_OP_STORE
        StoreOp.DONT_CARE -> VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE
    }

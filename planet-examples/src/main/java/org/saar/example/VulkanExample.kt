package org.saar.example


import org.lwjgl.BufferUtils
import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWFramebufferSizeCallback
import org.lwjgl.glfw.GLFWKeyCallbackI
import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.lwjgl.util.shaderc.*
import org.lwjgl.vulkan.*
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.FrontFace
import org.saar.rhi.resterization.PolygonMode
import org.saar.rhi.resterization.RasterizationState
import org.saar.rhi.vulkan.resterization.toVulkan
import java.io.*
import java.nio.ByteBuffer
import java.nio.IntBuffer
import java.nio.channels.Channels
import java.nio.channels.FileChannel


object VulkanExample {
    private const val DEBUG = true

    private val layers = arrayOf(
        "VK_LAYER_LUNARG_standard_validation",
        "VK_LAYER_KHRONOS_validation",
    )

    /**
     * This is just -1L, but it is nicer as a symbolic constant.
     */
    private const val UINT64_MAX = -0x1L

    /**
     * Create a Vulkan instance using LWJGL 3.
     *
     * @return the VkInstance handle
     */
    private fun createInstance(requiredExtensions: PointerBuffer): VkInstance {
        val appInfo = VkApplicationInfo.calloc()
            .`sType$Default`()
            .apiVersion(VK10.VK_API_VERSION_1_0)

        val VK_EXT_DEBUG_REPORT_EXTENSION = MemoryUtil.memUTF8(EXTDebugReport.VK_EXT_DEBUG_REPORT_EXTENSION_NAME)
        val ppEnabledExtensionNames = MemoryUtil.memAllocPointer(requiredExtensions.remaining() + 1)
            .put(requiredExtensions)
            .put(VK_EXT_DEBUG_REPORT_EXTENSION)
            .flip()

        val ppEnabledLayerNames = if (DEBUG) allocateLayerBuffer(layers) else null

        val pCreateInfo = VkInstanceCreateInfo.calloc()
            .`sType$Default`()
            .pApplicationInfo(appInfo)
            .ppEnabledExtensionNames(ppEnabledExtensionNames)
            .ppEnabledLayerNames(ppEnabledLayerNames)

        val pInstance = MemoryUtil.memAllocPointer(1)
        val err = VK10.vkCreateInstance(pCreateInfo, null, pInstance)
        val instance = pInstance.get(0)
        MemoryUtil.memFree(pInstance)

        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create VkInstance: " + translateVulkanResult(err))
        }

        val ret = VkInstance(instance, pCreateInfo)
        pCreateInfo.free()
        if (ppEnabledLayerNames != null) MemoryUtil.memFree(ppEnabledLayerNames)
        MemoryUtil.memFree(VK_EXT_DEBUG_REPORT_EXTENSION)
        MemoryUtil.memFree(ppEnabledExtensionNames)
        MemoryUtil.memFree(appInfo.pApplicationName())
        MemoryUtil.memFree(appInfo.pEngineName())
        appInfo.free()
        return ret
    }

    private fun setupDebugging(instance: VkInstance, flags: Int, callback: VkDebugReportCallbackEXTI): Long {
        val dbgCreateInfo = VkDebugReportCallbackCreateInfoEXT.calloc()
            .`sType$Default`()
            .pfnCallback(callback)
            .flags(flags)
        val pCallback = MemoryUtil.memAllocLong(1)
        val err = EXTDebugReport.vkCreateDebugReportCallbackEXT(instance, dbgCreateInfo, null, pCallback)
        val callbackHandle = pCallback.get(0)
        MemoryUtil.memFree(pCallback)
        dbgCreateInfo.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create VkInstance: " + translateVulkanResult(err))
        }
        return callbackHandle
    }

    private fun getFirstPhysicalDevice(instance: VkInstance): VkPhysicalDevice {
        val pPhysicalDeviceCount = MemoryUtil.memAllocInt(1)
        var err = VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, null)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to get number of physical devices: " + translateVulkanResult(err))
        }
        val pPhysicalDevices = MemoryUtil.memAllocPointer(pPhysicalDeviceCount.get(0))
        err = VK10.vkEnumeratePhysicalDevices(instance, pPhysicalDeviceCount, pPhysicalDevices)
        val physicalDevice = pPhysicalDevices.get(0)
        MemoryUtil.memFree(pPhysicalDeviceCount)
        MemoryUtil.memFree(pPhysicalDevices)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to get physical devices: " + translateVulkanResult(err))
        }
        return VkPhysicalDevice(physicalDevice, instance)
    }

    private fun createDeviceAndGetGraphicsQueueFamily(physicalDevice: VkPhysicalDevice): DeviceAndGraphicsQueueFamily {
        val pQueueFamilyPropertyCount = MemoryUtil.memAllocInt(1)
        VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, null)
        val queueCount = pQueueFamilyPropertyCount.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, queueProps)
        MemoryUtil.memFree(pQueueFamilyPropertyCount)
        var graphicsQueueFamilyIndex: Int
        graphicsQueueFamilyIndex = 0
        while (graphicsQueueFamilyIndex < queueCount) {
            if ((queueProps.get(graphicsQueueFamilyIndex).queueFlags() and VK10.VK_QUEUE_GRAPHICS_BIT) != 0) break
            graphicsQueueFamilyIndex++
        }
        queueProps.free()
        val pQueuePriorities = MemoryUtil.memAllocFloat(1).put(0.0f)
        pQueuePriorities.flip()
        val queueCreateInfo = VkDeviceQueueCreateInfo.calloc(1)
            .`sType$Default`()
            .queueFamilyIndex(graphicsQueueFamilyIndex)
            .pQueuePriorities(pQueuePriorities)

        val extensions = MemoryUtil.memAllocPointer(1)
        val VK_KHR_SWAPCHAIN_EXTENSION = MemoryUtil.memUTF8(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME)
        extensions.put(VK_KHR_SWAPCHAIN_EXTENSION)
        extensions.flip()

        val deviceCreateInfo = VkDeviceCreateInfo.calloc()
            .`sType$Default`()
            .pQueueCreateInfos(queueCreateInfo)
            .ppEnabledExtensionNames(extensions)

        val pDevice = MemoryUtil.memAllocPointer(1)
        val err = VK10.vkCreateDevice(physicalDevice, deviceCreateInfo, null, pDevice)
        val device = pDevice.get(0)
        MemoryUtil.memFree(pDevice)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create device: " + translateVulkanResult(err))
        }

        val memoryProperties = VkPhysicalDeviceMemoryProperties.calloc()
        VK10.vkGetPhysicalDeviceMemoryProperties(physicalDevice, memoryProperties)

        val ret = DeviceAndGraphicsQueueFamily()
        ret.device = VkDevice(device, physicalDevice, deviceCreateInfo)
        ret.queueFamilyIndex = graphicsQueueFamilyIndex
        ret.memoryProperties = memoryProperties

        deviceCreateInfo.free()
        MemoryUtil.memFree(VK_KHR_SWAPCHAIN_EXTENSION)
        MemoryUtil.memFree(extensions)
        MemoryUtil.memFree(pQueuePriorities)
        return ret
    }

    private fun getColorFormatAndSpace(physicalDevice: VkPhysicalDevice, surface: Long): ColorFormatAndSpace {
        val pQueueFamilyPropertyCount = MemoryUtil.memAllocInt(1)
        VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, null)
        val queueCount = pQueueFamilyPropertyCount.get(0)
        val queueProps = VkQueueFamilyProperties.calloc(queueCount)
        VK10.vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, pQueueFamilyPropertyCount, queueProps)
        MemoryUtil.memFree(pQueueFamilyPropertyCount)

        // Iterate over each queue to learn whether it supports presenting:
        val supportsPresent = MemoryUtil.memAllocInt(queueCount)
        for (i in 0..<queueCount) {
            supportsPresent.position(i)
            val err = KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(physicalDevice, i, surface, supportsPresent)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to physical device surface support: " + translateVulkanResult(err))
            }
        }

        // Search for a graphics and a present queue in the array of queue families, try to find one that supports both
        var graphicsQueueNodeIndex = Int.MAX_VALUE
        var presentQueueNodeIndex = Int.MAX_VALUE
        for (i in 0..<queueCount) {
            if ((queueProps.get(i).queueFlags() and VK10.VK_QUEUE_GRAPHICS_BIT) != 0) {
                if (graphicsQueueNodeIndex == Int.MAX_VALUE) {
                    graphicsQueueNodeIndex = i
                }
                if (supportsPresent.get(i) == VK10.VK_TRUE) {
                    graphicsQueueNodeIndex = i
                    presentQueueNodeIndex = i
                    break
                }
            }
        }
        queueProps.free()
        if (presentQueueNodeIndex == Int.MAX_VALUE) {
            // If there's no queue that supports both present and graphics try to find a separate present queue
            for (i in 0..<queueCount) {
                if (supportsPresent.get(i) == VK10.VK_TRUE) {
                    presentQueueNodeIndex = i
                    break
                }
            }
        }
        MemoryUtil.memFree(supportsPresent)

        // Generate error if could not find both a graphics and a present queue
        if (graphicsQueueNodeIndex == Int.MAX_VALUE) {
            throw AssertionError("No graphics queue found")
        }
        if (presentQueueNodeIndex == Int.MAX_VALUE) {
            throw AssertionError("No presentation queue found")
        }
        if (graphicsQueueNodeIndex != presentQueueNodeIndex) {
            throw AssertionError("Presentation queue != graphics queue")
        }

        // Get list of supported formats
        val pFormatCount = MemoryUtil.memAllocInt(1)
        var err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface, pFormatCount, null)
        val formatCount = pFormatCount.get(0)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError(
                "Failed to query number of physical device surface formats: " + translateVulkanResult(
                    err
                )
            )
        }

        val surfFormats = VkSurfaceFormatKHR.calloc(formatCount)
        err = KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(physicalDevice, surface, pFormatCount, surfFormats)
        MemoryUtil.memFree(pFormatCount)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to query physical device surface formats: " + translateVulkanResult(err))
        }

        val colorFormat: Int
        if (formatCount == 1 && surfFormats.get(0).format() == VK10.VK_FORMAT_UNDEFINED) {
            colorFormat = VK10.VK_FORMAT_B8G8R8A8_UNORM
        } else {
            colorFormat = surfFormats.get(0).format()
        }
        val colorSpace = surfFormats.get(0).colorSpace()
        surfFormats.free()

        val ret = ColorFormatAndSpace()
        ret.colorFormat = colorFormat
        ret.colorSpace = colorSpace
        return ret
    }

    private fun createCommandPool(device: VkDevice, queueNodeIndex: Int): Long {
        val cmdPoolInfo = VkCommandPoolCreateInfo.calloc()
            .`sType$Default`()
            .queueFamilyIndex(queueNodeIndex)
            .flags(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT)
        val pCmdPool = MemoryUtil.memAllocLong(1)
        val err = VK10.vkCreateCommandPool(device, cmdPoolInfo, null, pCmdPool)
        val commandPool = pCmdPool.get(0)
        cmdPoolInfo.free()
        MemoryUtil.memFree(pCmdPool)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create command pool: " + translateVulkanResult(err))
        }
        return commandPool
    }

    private fun createDeviceQueue(device: VkDevice, queueFamilyIndex: Int): VkQueue {
        val pQueue = MemoryUtil.memAllocPointer(1)
        VK10.vkGetDeviceQueue(device, queueFamilyIndex, 0, pQueue)
        val queue = pQueue.get(0)
        MemoryUtil.memFree(pQueue)
        return VkQueue(queue, device)
    }

    private fun createCommandBuffer(device: VkDevice, commandPool: Long): VkCommandBuffer {
        val cmdBufAllocateInfo = VkCommandBufferAllocateInfo.calloc()
            .`sType$Default`()
            .commandPool(commandPool)
            .level(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY)
            .commandBufferCount(1)
        val pCommandBuffer = MemoryUtil.memAllocPointer(1)
        val err = VK10.vkAllocateCommandBuffers(device, cmdBufAllocateInfo, pCommandBuffer)
        cmdBufAllocateInfo.free()
        val commandBuffer = pCommandBuffer.get(0)
        MemoryUtil.memFree(pCommandBuffer)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to allocate command buffer: " + translateVulkanResult(err))
        }
        return VkCommandBuffer(commandBuffer, device)
    }

    private fun createSwapChain(
        device: VkDevice,
        physicalDevice: VkPhysicalDevice,
        surface: Long,
        oldSwapChain: Long,
        newWidth: Int,
        newHeight: Int,
        colorFormat: Int,
        colorSpace: Int
    ): Swapchain {
        var err: Int
        // Get physical device surface properties and formats
        val surfCaps = VkSurfaceCapabilitiesKHR.calloc()
        err = KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(physicalDevice, surface, surfCaps)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to get physical device surface capabilities: " + translateVulkanResult(err))
        }

        var desiredNumberOfSwapchainImages = surfCaps.minImageCount()
        if ((surfCaps.maxImageCount() > 0) && (desiredNumberOfSwapchainImages > surfCaps.maxImageCount())) {
            desiredNumberOfSwapchainImages = surfCaps.maxImageCount()
        }

        val currentExtent = surfCaps.currentExtent()
        val currentWidth = currentExtent.width()
        val currentHeight = currentExtent.height()
        if (currentWidth != -1 && currentHeight != -1) {
            width = currentWidth
            height = currentHeight
        } else {
            width = newWidth
            height = newHeight
        }

        val preTransform: Int
        if ((surfCaps.supportedTransforms() and KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR) != 0) {
            preTransform = KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR
        } else {
            preTransform = surfCaps.currentTransform()
        }
        surfCaps.free()

        val swapchainCI = VkSwapchainCreateInfoKHR.calloc()
            .`sType$Default`()
            .surface(surface)
            .minImageCount(desiredNumberOfSwapchainImages)
            .imageFormat(colorFormat)
            .imageColorSpace(colorSpace)
            .imageUsage(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
            .preTransform(preTransform)
            .imageArrayLayers(1)
            .imageSharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE)
            .presentMode(KHRSurface.VK_PRESENT_MODE_FIFO_KHR)
            .oldSwapchain(oldSwapChain)
            .clipped(true)
            .compositeAlpha(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
        swapchainCI.imageExtent()
            .width(width)
            .height(height)
        val pSwapChain = MemoryUtil.memAllocLong(1)
        err = KHRSwapchain.vkCreateSwapchainKHR(device, swapchainCI, null, pSwapChain)
        swapchainCI.free()
        val swapChain = pSwapChain.get(0)
        MemoryUtil.memFree(pSwapChain)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create swap chain: " + translateVulkanResult(err))
        }

        // If we just re-created an existing swapchain, we should destroy the old swapchain at this point.
        // Note: destroying the swapchain also cleans up all its associated presentable images once the platform is done with them.
        if (oldSwapChain != VK10.VK_NULL_HANDLE) {
            KHRSwapchain.vkDestroySwapchainKHR(device, oldSwapChain, null)
        }

        val pImageCount = MemoryUtil.memAllocInt(1)
        err = KHRSwapchain.vkGetSwapchainImagesKHR(device, swapChain, pImageCount, null)
        val imageCount = pImageCount.get(0)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to get number of swapchain images: " + translateVulkanResult(err))
        }

        val pSwapchainImages = MemoryUtil.memAllocLong(imageCount)
        err = KHRSwapchain.vkGetSwapchainImagesKHR(device, swapChain, pImageCount, pSwapchainImages)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to get swapchain images: " + translateVulkanResult(err))
        }
        MemoryUtil.memFree(pImageCount)

        val images = LongArray(imageCount)
        val imageViews = LongArray(imageCount)
        val pBufferView = MemoryUtil.memAllocLong(1)
        val colorAttachmentView = VkImageViewCreateInfo.calloc()
            .`sType$Default`()
            .format(colorFormat)
            .viewType(VK10.VK_IMAGE_VIEW_TYPE_2D)
        colorAttachmentView.subresourceRange()
            .aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT)
            .levelCount(1)
            .layerCount(1)
        for (i in 0..<imageCount) {
            images[i] = pSwapchainImages.get(i)
            colorAttachmentView.image(images[i])
            err = VK10.vkCreateImageView(device, colorAttachmentView, null, pBufferView)
            imageViews[i] = pBufferView.get(0)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to create image view: " + translateVulkanResult(err))
            }
        }
        colorAttachmentView.free()
        MemoryUtil.memFree(pBufferView)
        MemoryUtil.memFree(pSwapchainImages)

        val ret = Swapchain()
        ret.images = images
        ret.imageViews = imageViews
        ret.swapchainHandle = swapChain
        return ret
    }

    private fun createRenderPass(device: VkDevice, colorFormat: Int): Long {
        val attachments = VkAttachmentDescription.calloc(1)
            .format(colorFormat)
            .samples(VK10.VK_SAMPLE_COUNT_1_BIT)
            .loadOp(VK10.VK_ATTACHMENT_LOAD_OP_CLEAR)
            .storeOp(VK10.VK_ATTACHMENT_STORE_OP_STORE)
            .stencilLoadOp(VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE)
            .stencilStoreOp(VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE)
            .initialLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)
            .finalLayout(KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR)

        val colorReference = VkAttachmentReference.calloc(1)
            .attachment(0)
            .layout(VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL)

        val subpass = VkSubpassDescription.calloc(1)
            .pipelineBindPoint(VK10.VK_PIPELINE_BIND_POINT_GRAPHICS)
            .colorAttachmentCount(colorReference.remaining())
            .pColorAttachments(colorReference)
        // <- only color attachment


        val dependency = VkSubpassDependency.calloc(1)
            .srcSubpass(VK10.VK_SUBPASS_EXTERNAL)
            .srcStageMask(VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
            .dstAccessMask(VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT)
            .dstStageMask(VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
            .dependencyFlags(VK10.VK_DEPENDENCY_BY_REGION_BIT)

        val renderPassInfo = VkRenderPassCreateInfo.calloc()
            .`sType$Default`()
            .pAttachments(attachments)
            .pSubpasses(subpass)
            .pDependencies(dependency)

        val pRenderPass = MemoryUtil.memAllocLong(1)
        val err = VK10.vkCreateRenderPass(device, renderPassInfo, null, pRenderPass)
        val renderPass = pRenderPass.get(0)
        MemoryUtil.memFree(pRenderPass)
        dependency.free()
        renderPassInfo.free()
        colorReference.free()
        subpass.free()
        attachments.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create clear render pass: " + translateVulkanResult(err))
        }
        return renderPass
    }

    private fun createFramebuffers(
        device: VkDevice,
        swapchain: Swapchain,
        renderPass: Long,
        width: Int,
        height: Int
    ): LongArray {
        val attachments = MemoryUtil.memAllocLong(1)
        val fci = VkFramebufferCreateInfo.calloc()
            .`sType$Default`()
            .pAttachments(attachments)
            .height(height)
            .width(width)
            .layers(1)
            .renderPass(renderPass)
        // Create a framebuffer for each swapchain image
        val framebuffers = LongArray(swapchain.images!!.size)
        val pFramebuffer = MemoryUtil.memAllocLong(1)
        for (i in swapchain.images!!.indices) {
            attachments.put(0, swapchain.imageViews!![i])
            val err = VK10.vkCreateFramebuffer(device, fci, null, pFramebuffer)
            val framebuffer = pFramebuffer.get(0)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to create framebuffer: " + translateVulkanResult(err))
            }
            framebuffers[i] = framebuffer
        }
        MemoryUtil.memFree(attachments)
        MemoryUtil.memFree(pFramebuffer)
        fci.free()
        return framebuffers
    }

    private fun submitCommandBuffer(queue: VkQueue, commandBuffer: VkCommandBuffer) {
        if (commandBuffer.address() == MemoryUtil.NULL) return
        val submitInfo = VkSubmitInfo.calloc()
            .`sType$Default`()
        val pCommandBuffers = MemoryUtil.memAllocPointer(1)
            .put(commandBuffer)
            .flip()
        submitInfo.pCommandBuffers(pCommandBuffers)
        val err = VK10.vkQueueSubmit(queue, submitInfo, VK10.VK_NULL_HANDLE)
        MemoryUtil.memFree(pCommandBuffers)
        submitInfo.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to submit command buffer: " + translateVulkanResult(err))
        }
    }

    private fun loadShader(classPath: String, device: VkDevice, stage: Int): Long {
        val shaderCode: ByteBuffer = glslToSpirv(classPath, stage)
        val err: Int
        val moduleCreateInfo = VkShaderModuleCreateInfo.calloc()
            .`sType$Default`()
            .pCode(shaderCode)
        val pShaderModule = MemoryUtil.memAllocLong(1)
        err = VK10.vkCreateShaderModule(device, moduleCreateInfo, null, pShaderModule)
        val shaderModule = pShaderModule.get(0)
        MemoryUtil.memFree(pShaderModule)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create shader module: " + translateVulkanResult(err))
        }
        return shaderModule
    }

    private fun loadShader(device: VkDevice, classPath: String, stage: Int): VkPipelineShaderStageCreateInfo {
        val shaderStage = VkPipelineShaderStageCreateInfo.calloc()
            .`sType$Default`()
            .stage(stage)
            .module(loadShader(classPath, device, stage))
            .pName(MemoryUtil.memUTF8("main"))
        return shaderStage
    }

    private fun getMemoryType(
        deviceMemoryProperties: VkPhysicalDeviceMemoryProperties,
        typeBits: Int,
        properties: Int,
        typeIndex: IntBuffer
    ): Boolean {
        var bits = typeBits
        for (i in 0..31) {
            if ((bits and 1) == 1) {
                if ((deviceMemoryProperties.memoryTypes(i).propertyFlags() and properties) == properties) {
                    typeIndex.put(0, i)
                    return true
                }
            }
            bits = bits shr 1
        }
        return false
    }

    private fun createVertices(deviceMemoryProperties: VkPhysicalDeviceMemoryProperties, device: VkDevice): Vertices {
        val vertexBuffer = MemoryUtil.memAlloc(3 * 2 * 4)
        val fb = vertexBuffer.asFloatBuffer()
        // The triangle will showup upside-down, because Vulkan does not do proper viewport transformation to
        // account for inverted Y axis between the window coordinate system and clip space/NDC
        fb.put(-0.5f).put(-0.5f)
        fb.put(0.5f).put(-0.5f)
        fb.put(0.0f).put(0.5f)

        val memAlloc = VkMemoryAllocateInfo.calloc()
            .`sType$Default`()
        val memReqs = VkMemoryRequirements.calloc()

        var err: Int

        // Generate vertex buffer
        //  Setup
        val bufInfo = VkBufferCreateInfo.calloc()
            .`sType$Default`()
            .size(vertexBuffer.remaining().toLong())
            .usage(VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT)
        val pBuffer = MemoryUtil.memAllocLong(1)
        err = VK10.vkCreateBuffer(device, bufInfo, null, pBuffer)
        val verticesBuf = pBuffer.get(0)
        MemoryUtil.memFree(pBuffer)
        bufInfo.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create vertex buffer: " + translateVulkanResult(err))
        }

        VK10.vkGetBufferMemoryRequirements(device, verticesBuf, memReqs)
        memAlloc.allocationSize(memReqs.size())
        val memoryTypeIndex = MemoryUtil.memAllocInt(1)
        getMemoryType(
            deviceMemoryProperties,
            memReqs.memoryTypeBits(),
            VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT,
            memoryTypeIndex
        )
        memAlloc.memoryTypeIndex(memoryTypeIndex.get(0))
        MemoryUtil.memFree(memoryTypeIndex)
        memReqs.free()

        val pMemory = MemoryUtil.memAllocLong(1)
        err = VK10.vkAllocateMemory(device, memAlloc, null, pMemory)
        val verticesMem = pMemory.get(0)
        MemoryUtil.memFree(pMemory)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to allocate vertex memory: " + translateVulkanResult(err))
        }

        val pData = MemoryUtil.memAllocPointer(1)
        err = VK10.vkMapMemory(device, verticesMem, 0, memAlloc.allocationSize(), 0, pData)
        memAlloc.free()
        val data = pData.get(0)
        MemoryUtil.memFree(pData)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to map vertex memory: " + translateVulkanResult(err))
        }

        MemoryUtil.memCopy(MemoryUtil.memAddress(vertexBuffer), data, vertexBuffer.remaining().toLong())
        MemoryUtil.memFree(vertexBuffer)
        VK10.vkUnmapMemory(device, verticesMem)
        err = VK10.vkBindBufferMemory(device, verticesBuf, verticesMem, 0)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to bind memory to vertex buffer: " + translateVulkanResult(err))
        }

        // Binding description
        val bindingDescriptor = VkVertexInputBindingDescription.calloc(1)
            .binding(0) // <- we bind our vertex buffer to point 0
            .stride(2 * 4)
            .inputRate(VK10.VK_VERTEX_INPUT_RATE_VERTEX)

        // Attribute descriptions
        // Describes memory layout and shader attribute locations
        val attributeDescriptions = VkVertexInputAttributeDescription.calloc(1)
        // Location 0 : Position
        attributeDescriptions.get(0)
            .binding(0) // <- binding point used in the VkVertexInputBindingDescription
            .location(0) // <- location in the shader's attribute layout (inside the shader source)
            .format(VK10.VK_FORMAT_R32G32_SFLOAT)
            .offset(0)

        // Assign to vertex buffer
        val vi = VkPipelineVertexInputStateCreateInfo.calloc()
            .`sType$Default`()
            .pVertexBindingDescriptions(bindingDescriptor)
            .pVertexAttributeDescriptions(attributeDescriptions)

        val ret = Vertices()
        ret.createInfo = vi
        ret.verticesBuf = verticesBuf
        return ret
    }

    private fun createPipeline(device: VkDevice, renderPass: Long, vi: VkPipelineVertexInputStateCreateInfo): Long {
        var err: Int
        // Vertex input state
        // Describes the topoloy used with this pipeline
        val inputAssemblyState = VkPipelineInputAssemblyStateCreateInfo.calloc()
            .`sType$Default`()
            .topology(VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST)

        // Rasterization state
        val rasterizationState = RasterizationState(
            polygonMode = PolygonMode.FILL,
            cullMode = CullMode.NONE,
            frontFace = FrontFace.COUNTER_CLOCKWISE,
            lineWidth = 1f
        ).toVulkan()

        // Color blend state
        // Describes blend modes and color masks
        val colorWriteMask = VkPipelineColorBlendAttachmentState.calloc(1)
            .colorWriteMask(0xF) // <- RGBA
        val colorBlendState = VkPipelineColorBlendStateCreateInfo.calloc()
            .`sType$Default`()
            .pAttachments(colorWriteMask)

        // Viewport state
        val viewportState = VkPipelineViewportStateCreateInfo.calloc()
            .`sType$Default`()
            .viewportCount(1) // <- one viewport
            .scissorCount(1) // <- one scissor rectangle

        // Enable dynamic states
        // Describes the dynamic states to be used with this pipeline
        // Dynamic states can be set even after the pipeline has been created
        // So there is no need to create new pipelines just for changing
        // a viewport's dimensions or a scissor box
        val pDynamicStates = MemoryUtil.memAllocInt(2)
        pDynamicStates.put(VK10.VK_DYNAMIC_STATE_VIEWPORT).put(VK10.VK_DYNAMIC_STATE_SCISSOR).flip()
        val dynamicState =
            VkPipelineDynamicStateCreateInfo.calloc() // The dynamic state properties themselves are stored in the command buffer
                .`sType$Default`()
                .pDynamicStates(pDynamicStates)

        // Depth and stencil state
        // Describes depth and stenctil test and compare ops
        val depthStencilState =
            VkPipelineDepthStencilStateCreateInfo.calloc() // No depth test/write and no stencil used
                .`sType$Default`()
                .depthCompareOp(VK10.VK_COMPARE_OP_ALWAYS)
        depthStencilState.back()
            .failOp(VK10.VK_STENCIL_OP_KEEP)
            .passOp(VK10.VK_STENCIL_OP_KEEP)
            .compareOp(VK10.VK_COMPARE_OP_ALWAYS)
        depthStencilState.front(depthStencilState.back())

        // Multi sampling state
        // No multi sampling used in this example
        val multisampleState = VkPipelineMultisampleStateCreateInfo.calloc()
            .`sType$Default`()
            .rasterizationSamples(VK10.VK_SAMPLE_COUNT_1_BIT)

        // Load shaders
        val shaderStages = VkPipelineShaderStageCreateInfo.calloc(2)
        shaderStages.get(0)
            .set(loadShader(device, "triangle.vertex.glsl", VK10.VK_SHADER_STAGE_VERTEX_BIT))
        shaderStages.get(1)
            .set(loadShader(device, "triangle.fragment.glsl", VK10.VK_SHADER_STAGE_FRAGMENT_BIT))

        // Create the pipeline layout that is used to generate the rendering pipelines that
        // are based on this descriptor set layout
        val pPipelineLayoutCreateInfo = VkPipelineLayoutCreateInfo.calloc()
            .`sType$Default`()

        val pPipelineLayout = MemoryUtil.memAllocLong(1)
        err = VK10.vkCreatePipelineLayout(device, pPipelineLayoutCreateInfo, null, pPipelineLayout)
        val layout = pPipelineLayout.get(0)
        MemoryUtil.memFree(pPipelineLayout)
        pPipelineLayoutCreateInfo.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create pipeline layout: " + translateVulkanResult(err))
        }

        // Assign states
        val pipelineCreateInfo = VkGraphicsPipelineCreateInfo.calloc(1)
            .`sType$Default`()
            .layout(layout) // <- the layout used for this pipeline (NEEDS TO BE SET! even though it is basically empty)
            .renderPass(renderPass) // <- renderpass this pipeline is attached to
            .pVertexInputState(vi)
            .pInputAssemblyState(inputAssemblyState)
            .pRasterizationState(rasterizationState)
            .pColorBlendState(colorBlendState)
            .pMultisampleState(multisampleState)
            .pViewportState(viewportState)
            .pDepthStencilState(depthStencilState)
            .pStages(shaderStages)
            .pDynamicState(dynamicState)

        // Create rendering pipeline
        val pPipelines = MemoryUtil.memAllocLong(1)
        err = VK10.vkCreateGraphicsPipelines(device, VK10.VK_NULL_HANDLE, pipelineCreateInfo, null, pPipelines)
        val pipeline = pPipelines.get(0)
        shaderStages.free()
        multisampleState.free()
        depthStencilState.free()
        dynamicState.free()
        MemoryUtil.memFree(pDynamicStates)
        viewportState.free()
        colorBlendState.free()
        colorWriteMask.free()
        rasterizationState.free()
        inputAssemblyState.free()
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create pipeline: " + translateVulkanResult(err))
        }
        return pipeline
    }

    private fun createRenderCommandBuffers(
        device: VkDevice, commandPool: Long, framebuffers: LongArray, renderPass: Long, width: Int, height: Int,
        pipeline: Long, verticesBuf: Long
    ): Array<VkCommandBuffer?> {
        // Create the render command buffers (one command buffer per framebuffer image)
        val cmdBufAllocateInfo = VkCommandBufferAllocateInfo.calloc()
            .`sType$Default`()
            .commandPool(commandPool)
            .level(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY)
            .commandBufferCount(framebuffers.size)
        val pCommandBuffer = MemoryUtil.memAllocPointer(framebuffers.size)
        var err = VK10.vkAllocateCommandBuffers(device, cmdBufAllocateInfo, pCommandBuffer)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to allocate render command buffer: " + translateVulkanResult(err))
        }
        val renderCommandBuffers = arrayOfNulls<VkCommandBuffer>(framebuffers.size)
        for (i in framebuffers.indices) {
            renderCommandBuffers[i] = VkCommandBuffer(pCommandBuffer.get(i), device)
        }
        MemoryUtil.memFree(pCommandBuffer)
        cmdBufAllocateInfo.free()

        // Create the command buffer begin structure
        val cmdBufInfo = VkCommandBufferBeginInfo.calloc()
            .`sType$Default`()

        // Specify clear color (cornflower blue)
        val clearValues = VkClearValue.calloc(1)
        clearValues.color()
            .float32(0, 100 / 255.0f)
            .float32(1, 149 / 255.0f)
            .float32(2, 237 / 255.0f)
            .float32(3, 1.0f)

        // Specify everything to begin a render pass
        val renderPassBeginInfo = VkRenderPassBeginInfo.calloc().apply {
            `sType$Default`()
            renderPass(renderPass)
            pClearValues(clearValues)
            renderArea().apply {
                offset().set(0, 0)
                extent().set(width, height)
            }
        }

        for (i in renderCommandBuffers.indices) {
            // Set target frame buffer
            renderPassBeginInfo.framebuffer(framebuffers[i])

            err = VK10.vkBeginCommandBuffer(renderCommandBuffers[i]!!, cmdBufInfo)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to begin render command buffer: " + translateVulkanResult(err))
            }

            VK10.vkCmdBeginRenderPass(renderCommandBuffers[i]!!, renderPassBeginInfo, VK10.VK_SUBPASS_CONTENTS_INLINE)

            // Update dynamic viewport state
            val viewport = VkViewport.calloc(1)
                .height(height.toFloat())
                .width(width.toFloat())
                .minDepth(0.0f)
                .maxDepth(1.0f)
            VK10.vkCmdSetViewport(renderCommandBuffers[i]!!, 0, viewport)
            viewport.free()

            // Update dynamic scissor state
            val scissor = VkRect2D.calloc(1)
            scissor.extent().set(width, height)
            scissor.offset().set(0, 0)
            VK10.vkCmdSetScissor(renderCommandBuffers[i]!!, 0, scissor)
            scissor.free()

            // Bind the rendering pipeline (including the shaders)
            VK10.vkCmdBindPipeline(renderCommandBuffers[i]!!, VK10.VK_PIPELINE_BIND_POINT_GRAPHICS, pipeline)

            // Bind triangle vertices
            val offsets = MemoryUtil.memAllocLong(1).apply { put(0, 0L) }
            val pBuffers = MemoryUtil.memAllocLong(1).apply { put(0, verticesBuf) }
            VK10.vkCmdBindVertexBuffers(renderCommandBuffers[i]!!, 0, pBuffers, offsets)
            MemoryUtil.memFree(pBuffers)
            MemoryUtil.memFree(offsets)

            // Draw triangle
            VK10.vkCmdDraw(renderCommandBuffers[i]!!, 3, 1, 0, 0)

            VK10.vkCmdEndRenderPass(renderCommandBuffers[i]!!)

            err = VK10.vkEndCommandBuffer(renderCommandBuffers[i]!!)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to begin render command buffer: " + translateVulkanResult(err))
            }
        }
        renderPassBeginInfo.free()
        clearValues.free()
        cmdBufInfo.free()
        return renderCommandBuffers
    }

    /*
     * All resources that must be reallocated on window resize.
     */
    private var swapchain: Swapchain? = null
    private var framebuffers: LongArray? = null
    private var width = 0
    private var height = 0
    private var renderCommandBuffers: Array<VkCommandBuffer?>? = null

    @JvmStatic
    fun main(args: Array<String>) {
        if (!GLFW.glfwInit()) {
            throw RuntimeException("Failed to initialize GLFW")
        }
        if (!GLFWVulkan.glfwVulkanSupported()) {
            throw AssertionError("GLFW failed to find the Vulkan loader")
        }

        /* Look for instance extensions */
        val requiredExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions()
        if (requiredExtensions == null) {
            throw AssertionError("Failed to find list of required Vulkan extensions")
        }

        // Create the Vulkan instance
        val instance = createInstance(requiredExtensions)
        val debugCallback = VkDebugReportCallbackEXTI { flags: Int,
                                                        objectType: Int,
                                                        obj: Long,
                                                        location: Long,
                                                        messageCode: Int,
                                                        pLayerPrefix: Long,
                                                        pMessage: Long,
                                                        pUserData: Long ->
            System.err.println("ERROR OCCURED: " + MemoryUtil.memUTF8(pMessage))
            0
        }
        val debugCallbackHandle = setupDebugging(
            instance,
            EXTDebugReport.VK_DEBUG_REPORT_ERROR_BIT_EXT or EXTDebugReport.VK_DEBUG_REPORT_WARNING_BIT_EXT,
            debugCallback
        )
        val physicalDevice = getFirstPhysicalDevice(instance)
        val deviceAndGraphicsQueueFamily = createDeviceAndGetGraphicsQueueFamily(physicalDevice)
        val device: VkDevice = deviceAndGraphicsQueueFamily.device!!
        val queueFamilyIndex = deviceAndGraphicsQueueFamily.queueFamilyIndex
        val memoryProperties: VkPhysicalDeviceMemoryProperties = deviceAndGraphicsQueueFamily.memoryProperties!!

        // Create GLFW window
        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        val window = GLFW.glfwCreateWindow(800, 600, "GLFW Vulkan Demo", MemoryUtil.NULL, MemoryUtil.NULL)
        val keyCallback = GLFWKeyCallbackI { window, key, scancode, action, mods ->
            if (action == GLFW.GLFW_RELEASE && key == GLFW.GLFW_KEY_ESCAPE) {
                GLFW.glfwSetWindowShouldClose(window, true)
            }
        }
        GLFW.glfwSetKeyCallback(window, keyCallback)
        val pSurface = MemoryUtil.memAllocLong(1)
        var err = GLFWVulkan.glfwCreateWindowSurface(instance, window, null, pSurface)
        val surface = pSurface.get(0)
        if (err != VK10.VK_SUCCESS) {
            throw AssertionError("Failed to create surface: " + translateVulkanResult(err))
        }

        // Create static Vulkan resources
        val colorFormatAndSpace = getColorFormatAndSpace(physicalDevice, surface)
        val commandPool = createCommandPool(device, queueFamilyIndex)
        val setupCommandBuffer = createCommandBuffer(device, commandPool)
        val queue = createDeviceQueue(device, queueFamilyIndex)
        val renderPass = createRenderPass(device, colorFormatAndSpace.colorFormat)
        val renderCommandPool = createCommandPool(device, queueFamilyIndex)
        val vertices = createVertices(memoryProperties, device)
        val pipeline = createPipeline(device, renderPass, vertices.createInfo!!)

        class SwapchainRecreator {
            var mustRecreate: Boolean = true
            fun recreate() {
                // Begin the setup command buffer (the one we will use for swapchain/framebuffer creation)
                val cmdBufInfo = VkCommandBufferBeginInfo.calloc()
                    .`sType$Default`()
                var err = VK10.vkBeginCommandBuffer(setupCommandBuffer, cmdBufInfo)
                cmdBufInfo.free()
                if (err != VK10.VK_SUCCESS) {
                    throw AssertionError("Failed to begin setup command buffer: " + translateVulkanResult(err))
                }
                val oldChain = if (swapchain != null) swapchain!!.swapchainHandle else VK10.VK_NULL_HANDLE
                // Create the swapchain (this will also add a memory barrier to initialize the framebuffer images)
                swapchain = createSwapChain(
                    device, physicalDevice, surface, oldChain,
                    width, height, colorFormatAndSpace.colorFormat, colorFormatAndSpace.colorSpace
                )
                err = VK10.vkEndCommandBuffer(setupCommandBuffer)
                if (err != VK10.VK_SUCCESS) {
                    throw AssertionError("Failed to end setup command buffer: " + translateVulkanResult(err))
                }
                submitCommandBuffer(queue, setupCommandBuffer)
                VK10.vkQueueWaitIdle(queue)

                if (framebuffers != null) {
                    for (i in framebuffers!!.indices) VK10.vkDestroyFramebuffer(device, framebuffers!![i], null)
                }
                framebuffers = createFramebuffers(device, swapchain!!, renderPass, width, height)
                // Create render command buffers
                if (renderCommandBuffers != null) {
                    VK10.vkResetCommandPool(device, renderCommandPool, VK_FLAGS_NONE)
                }
                renderCommandBuffers = createRenderCommandBuffers(
                    device, renderCommandPool, framebuffers!!, renderPass, width, height, pipeline,
                    vertices.verticesBuf
                )

                mustRecreate = false
            }
        }

        val swapchainRecreator = SwapchainRecreator()

        // Handle canvas resize
        val framebufferSizeCallback: GLFWFramebufferSizeCallback = object : GLFWFramebufferSizeCallback() {
            override fun invoke(window: Long, width: Int, height: Int) {
                if (width <= 0 || height <= 0) return
                VulkanExample.width = width
                VulkanExample.height = height
                swapchainRecreator.mustRecreate = true
            }
        }
        GLFW.glfwSetFramebufferSizeCallback(window, framebufferSizeCallback)
        GLFW.glfwShowWindow(window)

        // Pre-allocate everything needed in the render loop
        val pImageIndex = MemoryUtil.memAllocInt(1)
        var currentBuffer: Int
        val pCommandBuffers = MemoryUtil.memAllocPointer(1)
        val pSwapchains = MemoryUtil.memAllocLong(1)
        val pImageAcquiredSemaphore = MemoryUtil.memAllocLong(1)
        val pRenderCompleteSemaphore = MemoryUtil.memAllocLong(1)

        // Info struct to create a semaphore
        val semaphoreCreateInfo = VkSemaphoreCreateInfo.calloc()
            .`sType$Default`()

        // Info struct to submit a command buffer which will wait on the semaphore
        val pWaitDstStageMask = MemoryUtil.memAllocInt(1)
        pWaitDstStageMask.put(0, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
        val submitInfo = VkSubmitInfo.calloc()
            .`sType$Default`()
            .waitSemaphoreCount(pImageAcquiredSemaphore.remaining())
            .pWaitSemaphores(pImageAcquiredSemaphore)
            .pWaitDstStageMask(pWaitDstStageMask)
            .pCommandBuffers(pCommandBuffers)
            .pSignalSemaphores(pRenderCompleteSemaphore)

        // Info struct to present the current swapchain image to the display
        val presentInfo = VkPresentInfoKHR.calloc()
            .`sType$Default`()
            .pWaitSemaphores(pRenderCompleteSemaphore)
            .swapchainCount(pSwapchains.remaining())
            .pSwapchains(pSwapchains)
            .pImageIndices(pImageIndex)

        // The render loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Handle window messages. Resize events happen exactly here.
            // So it is safe to use the new swapchain images and framebuffers afterwards.
            GLFW.glfwPollEvents()
            if (swapchainRecreator.mustRecreate) swapchainRecreator.recreate()

            // Create a semaphore to wait for the swapchain to acquire the next image
            err = VK10.vkCreateSemaphore(device, semaphoreCreateInfo, null, pImageAcquiredSemaphore)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to create image acquired semaphore: " + translateVulkanResult(err))
            }

            // Create a semaphore to wait for the render to complete, before presenting
            err = VK10.vkCreateSemaphore(device, semaphoreCreateInfo, null, pRenderCompleteSemaphore)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to create render complete semaphore: " + translateVulkanResult(err))
            }

            // Get next image from the swap chain (back/front buffer).
            // This will setup the imageAquiredSemaphore to be signalled when the operation is complete
            err = KHRSwapchain.vkAcquireNextImageKHR(
                device,
                swapchain!!.swapchainHandle,
                UINT64_MAX,
                pImageAcquiredSemaphore.get(0),
                VK10.VK_NULL_HANDLE,
                pImageIndex
            )
            currentBuffer = pImageIndex.get(0)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to acquire next swapchain image: " + translateVulkanResult(err))
            }

            // Select the command buffer for the current framebuffer image/attachment
            pCommandBuffers.put(0, renderCommandBuffers!![currentBuffer]!!)

            // Submit to the graphics queue
            err = VK10.vkQueueSubmit(queue, submitInfo, VK10.VK_NULL_HANDLE)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to submit render queue: " + translateVulkanResult(err))
            }

            // Present the current buffer to the swap chain
            // This will display the image
            pSwapchains.put(0, swapchain!!.swapchainHandle)
            err = KHRSwapchain.vkQueuePresentKHR(queue, presentInfo)
            if (err != VK10.VK_SUCCESS) {
                throw AssertionError("Failed to present the swapchain image: " + translateVulkanResult(err))
            }
            // Create and submit post present barrier
            VK10.vkQueueWaitIdle(queue)

            // Destroy this semaphore (we will create a new one in the next frame)
            VK10.vkDestroySemaphore(device, pImageAcquiredSemaphore.get(0), null)
            VK10.vkDestroySemaphore(device, pRenderCompleteSemaphore.get(0), null)
        }
        presentInfo.free()
        MemoryUtil.memFree(pWaitDstStageMask)
        submitInfo.free()
        MemoryUtil.memFree(pImageAcquiredSemaphore)
        MemoryUtil.memFree(pRenderCompleteSemaphore)
        semaphoreCreateInfo.free()
        MemoryUtil.memFree(pSwapchains)
        MemoryUtil.memFree(pCommandBuffers)

        EXTDebugReport.vkDestroyDebugReportCallbackEXT(instance, debugCallbackHandle, null)

        framebufferSizeCallback.free()
        GLFW.glfwDestroyWindow(window)
        GLFW.glfwTerminate()

        // We don't bother disposing of all Vulkan resources.
        // Let the OS process manager take care of it.
    }

    private class DeviceAndGraphicsQueueFamily {
        var device: VkDevice? = null
        var queueFamilyIndex: Int = 0
        var memoryProperties: VkPhysicalDeviceMemoryProperties? = null
    }

    private class ColorFormatAndSpace {
        var colorFormat: Int = 0
        var colorSpace: Int = 0
    }

    private class Swapchain {
        var swapchainHandle: Long = 0
        var images: LongArray? = null
        var imageViews: LongArray? = null
    }

    private class Vertices {
        var verticesBuf: Long = 0
        var createInfo: VkPipelineVertexInputStateCreateInfo? = null
    }
}

/**
 * Utility functions for Vulkan.
 *
 * @author Kai Burjack
 */
const val VK_FLAGS_NONE: Int = 0

private fun vulkanStageToShadercKind(stage: Int): Int {
    return when (stage) {
        VK10.VK_SHADER_STAGE_VERTEX_BIT -> Shaderc.shaderc_vertex_shader
        VK10.VK_SHADER_STAGE_FRAGMENT_BIT -> Shaderc.shaderc_fragment_shader
        NVRayTracing.VK_SHADER_STAGE_RAYGEN_BIT_NV -> Shaderc.shaderc_raygen_shader
        NVRayTracing.VK_SHADER_STAGE_CLOSEST_HIT_BIT_NV -> Shaderc.shaderc_closesthit_shader
        NVRayTracing.VK_SHADER_STAGE_MISS_BIT_NV -> Shaderc.shaderc_miss_shader
        NVRayTracing.VK_SHADER_STAGE_ANY_HIT_BIT_NV -> Shaderc.shaderc_anyhit_shader
        NVRayTracing.VK_SHADER_STAGE_INTERSECTION_BIT_NV -> Shaderc.shaderc_intersection_shader
        VK10.VK_SHADER_STAGE_COMPUTE_BIT -> Shaderc.shaderc_compute_shader
        else -> throw IllegalArgumentException("Stage: $stage")
    }
}

fun glslToSpirv(classPath: String, vulkanStage: Int): ByteBuffer {
    val src: ByteBuffer = ioResourceToByteBuffer(classPath, 1024)
    val compiler: Long = Shaderc.shaderc_compiler_initialize()
    val options: Long = Shaderc.shaderc_compile_options_initialize()

    val resolver = ShadercIncludeResolveI { user_data: Long,
                                            requested_source: Long,
                                            type: Int,
                                            requesting_source: Long,
                                            include_depth: Long ->
        val res = ShadercIncludeResult.calloc()
        val src = classPath.substring(0, classPath.lastIndexOf('/')) + "/" + MemoryUtil.memUTF8(requested_source)
        res.content(ioResourceToByteBuffer(src, 1024))
        res.source_name(MemoryUtil.memUTF8(src))
        res.address()
    }
    val releaser = ShadercIncludeResultReleaseI { user_data: Long, include_result: Long ->
        val result = ShadercIncludeResult.create(include_result)
        MemoryUtil.memFree(result.source_name())
        result.free()
    }
    Shaderc.shaderc_compile_options_set_target_env(
        options,
        Shaderc.shaderc_target_env_vulkan,
        Shaderc.shaderc_env_version_vulkan_1_2
    )
    Shaderc.shaderc_compile_options_set_target_spirv(options, Shaderc.shaderc_spirv_version_1_4)
    Shaderc.shaderc_compile_options_set_optimization_level(options, Shaderc.shaderc_optimization_level_performance)
    Shaderc.shaderc_compile_options_set_include_callbacks(options, resolver, releaser, 0L)
    val res = MemoryStack.stackPush().use { stack ->
        Shaderc.shaderc_compile_into_spv(
            compiler,
            src,
            vulkanStageToShadercKind(vulkanStage),
            stack.UTF8(classPath),
            stack.UTF8("main"),
            options
        ).also { if (it == 0L) throw java.lang.AssertionError("Internal error during compilation!") }
    }
    if (Shaderc.shaderc_result_get_compilation_status(res) != Shaderc.shaderc_compilation_status_success) {
        throw java.lang.AssertionError("Shader compilation failed: " + Shaderc.shaderc_result_get_error_message(res))
    }
    val size = Shaderc.shaderc_result_get_length(res).toInt()
    val resultBytes = BufferUtils.createByteBuffer(size)
    resultBytes.put(Shaderc.shaderc_result_get_bytes(res))
    resultBytes.flip()
    Shaderc.shaderc_result_release(res)
    Shaderc.shaderc_compiler_release(compiler)
    return resultBytes
}

/**
 * Translates a Vulkan `VkResult` value to a String describing the result.
 *
 * @param result the `VkResult` value
 *
 * @return the result description
 */
fun translateVulkanResult(result: Int): String {
    when (result) {
        VK10.VK_SUCCESS -> return "Command successfully completed."
        VK10.VK_NOT_READY -> return "A fence or query has not yet completed."
        VK10.VK_TIMEOUT -> return "A wait operation has not completed in the specified time."
        VK10.VK_EVENT_SET -> return "An event is signaled."
        VK10.VK_EVENT_RESET -> return "An event is unsignaled."
        VK10.VK_INCOMPLETE -> return "A return array was too small for the result."
        KHRSwapchain.VK_SUBOPTIMAL_KHR -> return "A swapchain no longer matches the surface properties exactly, but can still be used to present to the surface successfully."
        VK10.VK_ERROR_OUT_OF_HOST_MEMORY -> return "A host memory allocation has failed."
        VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY -> return "A device memory allocation has failed."
        VK10.VK_ERROR_INITIALIZATION_FAILED -> return "Initialization of an object could not be completed for implementation-specific reasons."
        VK10.VK_ERROR_DEVICE_LOST -> return "The logical or physical device has been lost."
        VK10.VK_ERROR_MEMORY_MAP_FAILED -> return "Mapping of a memory object has failed."
        VK10.VK_ERROR_LAYER_NOT_PRESENT -> return "A requested layer is not present or could not be loaded."
        VK10.VK_ERROR_EXTENSION_NOT_PRESENT -> return "A requested extension is not supported."
        VK10.VK_ERROR_FEATURE_NOT_PRESENT -> return "A requested feature is not supported."
        VK10.VK_ERROR_INCOMPATIBLE_DRIVER -> return "The requested version of Vulkan is not supported by the driver or is otherwise incompatible for implementation-specific reasons."
        VK10.VK_ERROR_TOO_MANY_OBJECTS -> return "Too many objects of the type have already been created."
        VK10.VK_ERROR_FORMAT_NOT_SUPPORTED -> return "A requested format is not supported on this device."
        KHRSurface.VK_ERROR_SURFACE_LOST_KHR -> return "A surface is no longer available."
        KHRSurface.VK_ERROR_NATIVE_WINDOW_IN_USE_KHR -> return "The requested window is already connected to a VkSurfaceKHR, or to some other non-Vulkan API."
        KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR -> return ("A surface has changed in such a way that it is no longer compatible with the swapchain, and further presentation requests using the "
                + "swapchain will fail. Applications must query the new surface properties and recreate their swapchain if they wish to continue"
                + "presenting to the surface.")

        KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR -> return ("The display used by a swapchain does not use the same presentable image layout, or is incompatible in a way that prevents sharing an"
                + " image.")

        EXTDebugReport.VK_ERROR_VALIDATION_FAILED_EXT -> return "A validation layer found an error."
        else -> return String.format("%s [%d]", "Unknown", result)
    }
}

fun allocateLayerBuffer(layers: Array<String>): PointerBuffer {
    return MemoryUtil.memAllocPointer(layers.size).apply {
        println("Using layers:")

        for (layer in layers intersect availableLayers) {
            println("\t" + layer)
            put(MemoryUtil.memUTF8(layer))
        }
        flip()
    }
}

private val availableLayers: Set<String> by lazy {
    MemoryStack.stackPush().use { stack ->
        val ip = stack.mallocInt(1)
        VK10.vkEnumerateInstanceLayerProperties(ip, null)
        val count = ip[0]

        if (count > 0) {
            val instanceLayers = VkLayerProperties.malloc(count, stack)
            VK10.vkEnumerateInstanceLayerProperties(ip, instanceLayers)
            (0..<count).map { instanceLayers.get(it).layerNameString() }.toSet()
        } else emptySet()
    }
}

fun ioResourceToByteBuffer(resource: String, bufferSize: Int): ByteBuffer {
    val url = Thread.currentThread().contextClassLoader.getResource(resource)
        ?: throw IOException("Classpath resource not found: $resource")

    val file = File(url.file)

    return if (file.isFile) {
        FileInputStream(file).use { fis ->
            fis.channel.use { fc ->
                fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size())
            }
        }
    } else {
        val inputStream: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resource)
            ?: throw IOException("Resource not found on classpath or filesystem: $resource")

        inputStream.use { stream ->
            Channels.newChannel(stream).use { channel ->
                var buffer: ByteBuffer = BufferUtils.createByteBuffer(bufferSize)

                while (true) {
                    val bytesRead = channel.read(buffer)
                    if (bytesRead == -1) {
                        break // End of stream reached
                    }

                    // If our initial buffer guess was too small, resize dynamically
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2)
                    }
                }

                buffer.flip()
                return buffer
            }
        }
    }
}


private fun resizeBuffer(buffer: ByteBuffer, newCapacity: Int): ByteBuffer {
    return BufferUtils.createByteBuffer(newCapacity).apply {
        put(buffer.flip())
    }
}

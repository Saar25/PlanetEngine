package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercEnvVersion(val value: Int) {
    VULKAN_1_0(Shaderc.shaderc_env_version_vulkan_1_0),
    VULKAN_1_1(Shaderc.shaderc_env_version_vulkan_1_1),
    VULKAN_1_2(Shaderc.shaderc_env_version_vulkan_1_2),
    VULKAN_1_3(Shaderc.shaderc_env_version_vulkan_1_3),
    VULKAN_1_4(Shaderc.shaderc_env_version_vulkan_1_4),
    OPENGL_4_5(Shaderc.shaderc_env_version_opengl_4_5),
    WEBGPU(Shaderc.shaderc_env_version_webgpu),
}

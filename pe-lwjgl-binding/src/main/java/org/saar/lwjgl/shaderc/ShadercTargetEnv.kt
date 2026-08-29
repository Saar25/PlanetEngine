package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc

enum class ShadercTargetEnv(val value: Int) {
    VULKAN(Shaderc.shaderc_target_env_vulkan),
    OPENGL(Shaderc.shaderc_target_env_opengl),
    OPENGL_COMPAT(Shaderc.shaderc_target_env_opengl_compat),
    WEBGPU(Shaderc.shaderc_target_env_webgpu),
}

enum class ShadercSpirvVersion(val value: Int) {
    V1_0(Shaderc.shaderc_spirv_version_1_0),
    V1_1(Shaderc.shaderc_spirv_version_1_1),
    V1_2(Shaderc.shaderc_spirv_version_1_2),
    V1_3(Shaderc.shaderc_spirv_version_1_3),
    V1_4(Shaderc.shaderc_spirv_version_1_4),
    V1_5(Shaderc.shaderc_spirv_version_1_5),
    V1_6(Shaderc.shaderc_spirv_version_1_6),
}

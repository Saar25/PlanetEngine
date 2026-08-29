package org.saar.rhi.shader

data class ShaderStage(
    val module: ShaderModule,
    val type: ShaderStageType,
    val entryPoint: String? = null,
)

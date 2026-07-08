package org.saar.rhi.shader

fun ShaderProgram(vararg stages: ShaderStage): ShaderProgram {
    return ShaderProgram(stages.asList())
}

data class ShaderProgram(
    val stages: List<ShaderStage>,
)

package org.saar.core.shaders

import org.saar.rhi.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.rhi.shader.ShaderModule
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType

class ShaderBuilder(private val type: ShaderStageType) {

    var version: GlslVersion = GlslVersion.V400

    val shaderCodes = mutableListOf<ShaderCode>()

    fun build(): ShaderStage = ShaderStage(
        type = this.type,
        module = ShaderModule.fromString(this.version.toString() + this.shaderCodes.joinToString("\n") { it.code }),
    )
}

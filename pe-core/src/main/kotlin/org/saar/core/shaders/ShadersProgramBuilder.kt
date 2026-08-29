package org.saar.core.shaders

import org.saar.rhi.shader.ShaderProgram
import org.saar.rhi.shader.ShaderStage

class ShadersProgramBuilder {

    val shaders = mutableListOf<ShaderStage>()

    fun build() = ShaderProgram(this.shaders)
}

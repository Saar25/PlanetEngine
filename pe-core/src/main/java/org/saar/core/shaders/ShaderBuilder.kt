package org.saar.core.shaders

import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShaderType

class ShaderBuilder(private val type: ShaderType) {

    var version: GlslVersion = GlslVersion.V400

    val shaderCodes = mutableListOf<ShaderCode>()

    fun build(): Shader = Shader.of(this.type, this.version, *this.shaderCodes.toTypedArray())
}

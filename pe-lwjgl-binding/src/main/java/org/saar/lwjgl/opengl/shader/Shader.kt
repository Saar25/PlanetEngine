package org.saar.lwjgl.opengl.shader

import org.saar.rhi.opengl.shader.OpenglShaderStage
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.ShaderModule
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType

@Deprecated("Use ShaderStage or OpenglShaderStage instead")
class Shader(private val openglShaderStage: OpenglShaderStage) {

    fun attach(programId: Int) = this.openglShaderStage.attach(programId)

    fun detach(programId: Int) = this.openglShaderStage.detach(programId)

    fun delete() = this.openglShaderStage.delete()

    companion object {
        fun of(type: ShaderStageType, version: GlslVersion, vararg sources: ShaderCode): Shader {
            val moduleCode = getSources(version, *sources)
            val shaderStage = ShaderStage(
                module = ShaderModule.fromString(moduleCode),
                type = type,
                entryPoint = "main",
            )
            return Shader(shaderStage.toOpengl())
        }

        private fun getSources(version: GlslVersion, vararg sources: ShaderCode): String {
            return version.toString() + "\n" + sources.joinToString("\n") { it.code }
        }

        fun createVertex(version: GlslVersion, vararg sources: ShaderCode): Shader {
            return of(ShaderStageType.VERTEX, version, *sources)
        }

        @JvmStatic
        @Throws(Exception::class)
        fun createVertex(source: String): Shader {
            return of(ShaderStageType.VERTEX, GlslVersion.NONE, ShaderCode.loadSource(source))
        }

        fun createFragment(version: GlslVersion, vararg sources: ShaderCode): Shader {
            return of(ShaderStageType.FRAGMENT, version, *sources)
        }

        @JvmStatic
        @Throws(Exception::class)
        fun createFragment(source: String): Shader {
            return of(ShaderStageType.FRAGMENT, GlslVersion.NONE, ShaderCode.loadSource(source))
        }
    }
}
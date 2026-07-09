package org.saar.lwjgl.opengl.shader

import org.saar.rhi.opengl.shader.*
import org.saar.rhi.shader.ShaderProgram
import org.saar.rhi.shader.ShaderStage

@Deprecated("Use ShaderProgram or OpenglShaderProgram instead")
class ShadersProgram private constructor(val openglShadersProgram: OpenglShaderProgram) {

    fun bindAttribute(location: Int, name: String) = this.openglShadersProgram.bindAttribute(location, name)

    fun bindAttributes(vararg names: String) = this.openglShadersProgram.bindAttributes(*names)

    fun bindFragmentOutput(location: Int, name: String) = this.openglShadersProgram.bindFragmentOutput(location, name)

    fun bindFragmentOutputs(vararg names: String) = this.openglShadersProgram.bindFragmentOutputs(*names)

    fun getUniformLocation(name: String): Int = this.openglShadersProgram.getUniformLocation(name)

    fun bind() = this.openglShadersProgram.bind()

    fun unbind() = this.openglShadersProgram.unbind()

    fun delete() = this.openglShadersProgram.delete()

    companion object {
        @JvmStatic
        fun create(vararg stages: ShaderStage): ShadersProgram {
            val shaderProgram = ShaderProgram(*stages)
            val openglShadersProgram = shaderProgram.toOpengl()
            return ShadersProgram(openglShadersProgram)
        }

        @JvmStatic
        fun create(vertexShader: Shader, fragmentShader: Shader): ShadersProgram {
            val openglShadersProgram = OpenglShaderProgram.create(
                listOf(
                    vertexShader.openglShaderStage,
                    fragmentShader.openglShaderStage,
                )
            )
            return ShadersProgram(openglShadersProgram)
        }

        fun create(vararg shaders: Shader): ShadersProgram {
            val openglShadersProgram = OpenglShaderProgram.create(
                shaders.map(Shader::openglShaderStage)
            )
            return ShadersProgram(openglShadersProgram)
        }
    }
}

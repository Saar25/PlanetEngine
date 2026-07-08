package org.saar.rhi.opengl.shader

import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.saar.rhi.shader.ShaderProgram
import org.saar.rhi.shader.ShaderStage

fun ShaderProgram.toOpengl(): OpenglShaderProgram {
    val shaders = this.stages.map(ShaderStage::toOpengl)

    return OpenglShaderProgram.create(shaders)
}

class OpenglShaderProgram private constructor(private val id: Int) {

    fun bindAttribute(location: Int, name: String) = GL20.glBindAttribLocation(this.id, location, name)

    fun bindAttributes(vararg names: String) = names.forEachIndexed { index, string -> bindAttribute(index, string) }

    fun bindFragmentOutput(location: Int, name: String) = GL30.glBindFragDataLocation(this.id, location, name)

    fun bindFragmentOutputs(vararg names: String) {
        for (i in names.indices) {
            bindFragmentOutput(i, names[i])
        }
    }

    fun getUniformLocation(name: String) = GL20.glGetUniformLocation(this.id, name)

    fun bind() = GL20.glUseProgram(this.id)

    fun unbind() = GL20.glUseProgram(0)

    fun delete() = GL20.glDeleteProgram(this.id)

    companion object {
        fun create(shaders: Iterable<OpenglShaderStage>): OpenglShaderProgram {
            val id = GL20.glCreateProgram()

            shaders.forEach { it.attach(id) }
            GL20.glLinkProgram(id)
            GL20.glValidateProgram(id)

            if (GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
                val log = GL20.glGetProgramInfoLog(id)
                GL20.glDeleteProgram(id)
                shaders.forEach(OpenglShaderStage::delete)
                throw RuntimeException("Shader program link failed: $log")
            }

            shaders.forEach {
                it.detach(id)
                it.delete()
            }

            return OpenglShaderProgram(id)
        }
    }
}


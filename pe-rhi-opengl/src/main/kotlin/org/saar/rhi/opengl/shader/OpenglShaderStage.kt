package org.saar.rhi.opengl.shader

import org.lwjgl.opengl.GL20
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

fun ShaderStage.toOpengl(): OpenglShaderStage {
    val source = decodeUtf8(this.module.code)

    return OpenglShaderStage.compile(this.type, source)
}

private fun decodeUtf8(buffer: ByteBuffer): String {
    val bytes = ByteArray(buffer.remaining())
    buffer.duplicate().get(bytes)
    return String(bytes, StandardCharsets.UTF_8)
}

class OpenglShaderStage private constructor(val id: Int) {

    fun attach(programId: Int) = GL20.glAttachShader(programId, this.id)

    fun detach(programId: Int) = GL20.glDetachShader(programId, this.id)

    fun delete() = GL20.glDeleteShader(this.id)

    companion object {
        fun compile(stageType: ShaderStageType, source: String): OpenglShaderStage {
            val id = GL20.glCreateShader(stageType.glValue).also {
                GL20.glShaderSource(it, source)
                GL20.glCompileShader(it)

                if (GL20.glGetShaderi(it, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
                    val log = GL20.glGetShaderInfoLog(it)
                    GL20.glDeleteShader(it)
                    throw ShaderCompileException("$stageType shader compilation failed: $log")
                }
            }

            return OpenglShaderStage(id)
        }
    }
}

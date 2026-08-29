package org.saar.lwjgl.shaderc

import org.lwjgl.util.shaderc.Shaderc
import org.saar.rhi.shader.ShaderStageType
import java.nio.ByteBuffer

class ShadercCompiler internal constructor(val handle: Long) : AutoCloseable {

    fun compile(
        source: ByteBuffer,
        type: ShaderStageType,
        fileName: ByteBuffer,
        entryPoint: ByteBuffer,
        options: ShadercCompileOptions? = null,
    ): ShadercCompilationResult {
        val result = Shaderc.shaderc_compile_into_spv(
            this.handle, source, type.shadercValue, fileName, entryPoint, options?.handle ?: 0L
        )
        return ShadercCompilationResult(result)
    }

    fun compileOrThrow(
        source: ByteBuffer,
        type: ShaderStageType,
        fileName: ByteBuffer,
        entryPoint: ByteBuffer,
        options: ShadercCompileOptions? = null,
    ): ShadercCompilationResult {
        return compile(source, type, fileName, entryPoint, options).also { result ->
            if (!result.isSuccess) {
                val message = result.errorMessage ?: "Unknown shaderc compilation error"
                throw ShadercException("[${result.status}]: $message")
            }
        }

    }

    override fun close() = Shaderc.shaderc_compiler_release(this.handle)

    companion object {
        fun create(): ShadercCompiler {
            val handle = Shaderc.shaderc_compiler_initialize()

            return ShadercCompiler(handle)
        }
    }
}

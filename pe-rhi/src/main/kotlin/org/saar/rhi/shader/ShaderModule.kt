package org.saar.rhi.shader

import java.nio.ByteBuffer

data class ShaderModule(
    val code: ByteBuffer,
) {
    companion object {
        @JvmStatic
        fun load(file: String): ShaderModule {
            val source = ShaderModuleLoader.loadSource(file)
            val array = source.toByteArray(Charsets.UTF_8)
            val buffer = ByteBuffer.wrap(array)
            return ShaderModule(buffer)
        }
        @JvmStatic
        fun fromString(source: String): ShaderModule {
            val array = source.toByteArray(Charsets.UTF_8)
            val buffer = ByteBuffer.wrap(array)
            return ShaderModule(buffer)
        }
    }
}

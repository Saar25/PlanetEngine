package org.saar.lwjgl.opengl.shader

import org.saar.rhi.shader.ShaderModuleLoader

class ShaderCode private constructor(val code: String) {

    companion object {
        fun define(name: String, value: String): ShaderCode {
            return ShaderCode("#define $name $value")
        }

        @Throws(Exception::class)
        fun loadSource(file: String): ShaderCode {
            val code = ShaderModuleLoader.loadSource(file)

            return ShaderCode(code)
        }
    }
}

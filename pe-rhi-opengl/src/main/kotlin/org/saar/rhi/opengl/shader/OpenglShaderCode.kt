package org.saar.rhi.opengl.shader

class OpenglShaderCode private constructor(val code: String) {
    companion object {
        fun define(name: String, value: String) = OpenglShaderCode("#define $name $value")

        fun code(code: String) = OpenglShaderCode(code)
    }
}

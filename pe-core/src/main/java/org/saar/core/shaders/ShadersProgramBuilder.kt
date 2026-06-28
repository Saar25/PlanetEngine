package org.saar.core.shaders

import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShadersProgram

class ShadersProgramBuilder {

    val shaders = mutableListOf<Shader>()

    fun build(): ShadersProgram {
        return ShadersProgram.create(*this.shaders.toTypedArray())
    }
}

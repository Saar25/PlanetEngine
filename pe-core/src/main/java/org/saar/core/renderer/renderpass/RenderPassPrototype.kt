package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode

private val vertexShaderCode = ShaderCode.loadSource(
    "/shaders/common/quad/quad.vertex.glsl")

interface RenderPassPrototype {

    val vertexShader: Shader get() = Shader.createVertex(GlslVersion.V400, vertexShaderCode)

    val fragmentShader: Shader

}
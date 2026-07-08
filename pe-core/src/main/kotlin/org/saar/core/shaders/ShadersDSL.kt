package org.saar.core.shaders

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec4UniformValue
import org.saar.rhi.shader.ShaderStageType

fun <C : RenderContext, T> renderer(block: RendererBuilder<C, T>.() -> Unit): Renderer<C, T> {
    return RendererBuilder<C, T>().apply(block).build()
}

fun <C : RenderContext, T> RendererBuilder<C, T>.onRender(block: DoRender<C, T>) {
    doRender = block
}

inline fun RendererBuilder<out RenderContext, out Any>.shadersLink(block: ShadersLinkBuilder.() -> Unit): ShadersLink {
    return ShadersLinkBuilder().apply(block).build().also { shadersLink = it }
}

inline fun <T : UniformContainer> ShadersLinkBuilder.uniform(block: ShadersLinkBuilder.() -> T): T {
    return block().also { uniforms += it }
}

fun ShadersLinkBuilder.uniformMat4(name: String) = uniform { Mat4UniformValue(name) }
fun ShadersLinkBuilder.uniformVec4(name: String) = uniform { Vec4UniformValue(name) }
fun ShadersLinkBuilder.uniformFloat(name: String) = uniform { FloatUniformValue(name) }

inline fun ShadersLinkBuilder.shadersProgram(block: ShadersProgramBuilder.() -> Unit): ShadersProgram {
    return ShadersProgramBuilder().apply(block).build().also { shadersProgram = it }
}

inline fun ShadersProgramBuilder.shader(type: ShaderStageType, block: ShaderBuilder.() -> Unit): Shader {
    return ShaderBuilder(type).apply(block).build().also { shaders += it }
}

inline fun ShadersProgramBuilder.vertex(block: ShaderBuilder.() -> Unit): Shader {
    return shader(ShaderStageType.VERTEX, block)
}

inline fun ShadersProgramBuilder.fragment(block: ShaderBuilder.() -> Unit): Shader {
    return shader(ShaderStageType.FRAGMENT, block)
}

inline fun ShaderBuilder.code(block: () -> ShaderCode): ShaderCode {
    return block().also { shaderCodes += it }
}

inline fun ShaderBuilder.source(block: () -> String): ShaderCode {
    return code { block().let { ShaderCode.loadSource(it) } }
}

fun ShaderBuilder.define(name: String, value: String): ShaderCode {
    return code { ShaderCode.define(name, value) }
}
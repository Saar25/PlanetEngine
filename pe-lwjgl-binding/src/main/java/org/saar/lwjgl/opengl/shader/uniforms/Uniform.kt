package org.saar.lwjgl.opengl.shader.uniforms

interface Uniform : UniformContainer {
    val name: String
    fun load(location: Int)

    override val subUniforms: List<Uniform> get() = listOf(this)
}
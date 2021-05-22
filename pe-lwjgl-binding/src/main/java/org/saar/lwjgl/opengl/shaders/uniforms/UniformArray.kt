package org.saar.lwjgl.opengl.shaders.uniforms

import org.saar.lwjgl.opengl.shaders.ShadersProgram

class UniformArray<T : Uniform>(private val name: String, private val size: Int,
                                private val supplier: (name: String, index: Int) -> T) :
    Uniform, Iterable<T> {

    private val uniforms: List<T> = (0 until this.size).map {
        this.supplier("${this.name}[$it]", it)
    }

    override fun initialize(shadersProgram: ShadersProgram) {
        this.uniforms.forEach { it.initialize(shadersProgram) }
    }

    override fun load() = this.uniforms.forEach { it.load() }

    override fun iterator() = this.uniforms.iterator()
}
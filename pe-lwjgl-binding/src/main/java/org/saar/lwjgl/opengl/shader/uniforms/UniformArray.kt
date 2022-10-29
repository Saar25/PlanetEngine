package org.saar.lwjgl.opengl.shader.uniforms

class UniformArray<T : UniformContainer>(
    name: String, size: Int, supplier: (String, Int) -> T
) : Iterable<T>, UniformContainer {

    private val array = (0 until size).map { supplier("$name[$it]", it) }

    override val subUniforms = array.flatMap { it.subUniforms }

    override fun iterator() = this.array.listIterator()
}
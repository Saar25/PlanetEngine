package org.saar.core.common.inference.weak

import org.saar.core.mesh.Vertex
import org.saar.lwjgl.opengl.primitive.GlPrimitive
import org.saar.lwjgl.util.DataWriter

class WeakVertex(private val primitives: Iterable<GlPrimitive>) : Vertex {

    fun write(writer: DataWriter) = this.primitives.forEach { it.write(writer) }

    val attributes = this.primitives.flatMapIndexed { index, primitive ->
        primitive.attribute(index, false, 0).asIterable()
    }

    class Builder {
        private val primitives = mutableListOf<GlPrimitive>()

        fun with(primitive: GlPrimitive): Builder {
            this.primitives.add(primitive)
            return this
        }

        fun build() = WeakVertex(this.primitives)
    }
}

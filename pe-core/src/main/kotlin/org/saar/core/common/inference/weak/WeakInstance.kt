package org.saar.core.common.inference.weak

import org.saar.core.mesh.Instance
import org.saar.lwjgl.opengl.attribute.AttributeComposite
import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.primitive.GlPrimitive
import org.saar.lwjgl.util.DataWriter

class WeakInstance(private val primitives: List<GlPrimitive>) : Instance {

    fun getAttribute(vertexAttributes: Int): IAttribute {
        var index = vertexAttributes
        val attributes = this.primitives.flatMap {
            it.attribute(index++, false, 1).asIterable()
        }
        return AttributeComposite(attributes)
    }

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

        fun build() = WeakInstance(this.primitives)
    }
}

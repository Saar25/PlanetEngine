package org.saar.lwjgl.opengl.attribute

class AttributeComposite(private val attributes: Iterable<IAttribute>) : IAttribute {

    constructor(vararg attributes: IAttribute) : this(attributes.asIterable())

    override fun enable() = this.attributes.forEach(IAttribute::enable)

    override fun disable() = this.attributes.forEach(IAttribute::disable)

    override fun link(stride: Int, offset: Int) {
        this.attributes.fold(offset) { offsetAcc, attribute ->
            attribute.link(stride, offsetAcc)
            offsetAcc + attribute.bytesPerVertex
        }
    }

    override val bytesPerVertex get() = this.attributes.sumOf { it.bytesPerVertex }
}
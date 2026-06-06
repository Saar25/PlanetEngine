package org.saar.core.renderer

class RenderPipeline(private val passes: Iterable<RenderPass>) {

    constructor(vararg passes: RenderPass) : this(passes.asIterable())

    fun render(context: RenderContext) = this.passes.forEach { it.render(context) }

    fun delete() = this.passes.forEach { it.delete() }
}
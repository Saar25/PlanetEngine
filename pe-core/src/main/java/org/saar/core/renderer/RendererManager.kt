package org.saar.core.renderer

class RendererManager(private vararg val renderers: Renderer) : Renderer {

    override fun render(context: RenderContext) {
        this.renderers.forEach { it.render(context) }
    }

    override fun delete() {
        this.renderers.forEach { it.delete() }
    }
}
package org.saar.core.renderer

interface RenderNode {

    fun render(context: RenderContext)

    fun delete() = Unit

}
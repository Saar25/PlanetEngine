package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.ElementStyle
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.objects.Rectangle
import org.saar.maths.utils.Maths

class UIElement : UIChildNode, UIParentNode {

    var texture: ReadOnlyTexture = Texture2D.NULL

    var discardMap: ReadOnlyTexture = Texture2D.NULL

    override val style = ElementStyle(this)

    override var parent: UIParentNode = UINullNode

    private val _children = mutableListOf<UINode>()
    override val children: List<UINode> = this._children

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun renderForward(context: RenderContext) = render(context)

    override fun render2D(context: RenderContext) = render(context)

    override fun delete() {
        this.texture.delete()
        this.discardMap.delete()
        this.children.forEach { it.delete() }
    }

    override fun render(context: RenderContext) {
        UIBlockRenderer.render(context, this)
        this.children.forEach { it.render(context) }
    }

    fun add(uiNode: UIChildNode) {
        this._children.add(uiNode)
        uiNode.parent = this
    }

    override fun contains(x: Int, y: Int): Boolean {
        val ex = style.position.getX().toFloat()
        val ey = style.position.getY().toFloat()
        val ew = style.width.get().toFloat()
        val eh = style.height.get().toFloat()

        if (style.radiuses.isZero()) {
            return Maths.isBetween(x.toFloat(), ex, ex + ew) &&
                    Maths.isBetween(y.toFloat(), ey, ey + eh)
        }

        val radius = style.radiuses.get(
            x > ex + ew / 2,
            y < ey + eh / 2
        ).toFloat()

        val bordersV = Rectangle(ex + radius, ey, ew - radius * 2, eh)
        val bordersH = Rectangle(ex, ey + radius, ew, eh - radius * 2)

        if (bordersV.contains(x.toFloat(), y.toFloat()) ||
            bordersH.contains(x.toFloat(), y.toFloat())
        ) return true

        val cx = if (x < ex + ew / 2) ex + radius else ex + ew - radius

        val cy = if (y < ey + eh / 2) ey + radius else ey + eh - radius

        val dx = cx - x
        val dy = cy - y
        return radius * radius > dx * dx + dy * dy
    }
}
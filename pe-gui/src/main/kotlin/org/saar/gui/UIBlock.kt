package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.BlockStyle
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.objects.Rectangle
import org.saar.maths.utils.Maths

class UIBlock : UIChildNode {

    var discardMap: ReadOnlyTexture = Texture2D.NULL

    override var parent: UIParentNode = UINullNode

    override val style = BlockStyle(this)

    override fun render(context: RenderContext) = UIBlockRenderer.render(context, this)

    override fun contains(x: Int, y: Int): Boolean {
        val ex = style.position.getX().toFloat()
        val ey = style.position.getY().toFloat()
        val ew = style.width.get().toFloat()
        val eh = style.height.get().toFloat()

        if (style.radius.isZero()) {
            return Maths.isBetween(x.toFloat(), ex, ex + ew) &&
                    Maths.isBetween(y.toFloat(), ey, ey + eh)
        }

        val radius = style.radius.get(
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

    override fun delete() {
        this.style.backgroundImage.texture.delete()
        this.discardMap.delete()
    }
}

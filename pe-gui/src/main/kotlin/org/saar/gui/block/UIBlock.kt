package org.saar.gui.block

import org.saar.gui.style.IStyle
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.maths.objects.Rectangle
import org.saar.maths.utils.Maths

/**
 * This class represent an object inside a UIComponent.
 * Every UIComponent is composed by at least one UIObject which
 * will be rendered by the renderer.
 *
 * @author Saar ----
 * @version 1.2
 * @since 18.2.2018
 */
class UIBlock(val style: IStyle) {

    var texture: ReadOnlyTexture? = null

    var discardMap: ReadOnlyTexture? = null

    fun inTouch(mx: Int, my: Int): Boolean {
        val radiuses = style.radiuses

        val x = style.position.getX().toFloat()
        val y = style.position.getY().toFloat()
        val w = style.width.get().toFloat()
        val h = style.height.get().toFloat()

        if (radiuses.isZero()) {
            return Maths.isBetween(mx.toFloat(), x, x + w) &&
                    Maths.isBetween(my.toFloat(), y, y + h)
        }

        val radius = radiuses.get(mx > x + w / 2, my < y + h / 2).toFloat()

        val bordersV = Rectangle(x + radius, y, w - radius * 2, h)
        val bordersH = Rectangle(x, y + radius, w, h - radius * 2)

        if (bordersV.contains(mx.toFloat(), my.toFloat()) ||
            bordersH.contains(mx.toFloat(), my.toFloat())
        ) return true

        val cx = if (mx < x + w / 2) x + radius else x + w - radius

        val cy = if (my < y + h / 2) y + radius else y + h - radius

        val dx = cx - mx
        val dy = cy - my
        return radius * radius > dx * dx + dy * dy
    }

    fun delete() {
        this.texture?.delete()
        this.discardMap?.delete()
    }
}
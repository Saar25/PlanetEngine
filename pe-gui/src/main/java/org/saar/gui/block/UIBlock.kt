package org.saar.gui.block

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.core.mesh.common.QuadMesh
import org.saar.gui.UIChildElement
import org.saar.gui.UIElement
import org.saar.gui.UINullElement
import org.saar.gui.style.Style
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.objects.Rectangle

/**
 * This class represent an object inside a UIComponent.
 * Every UIComponent is composed by at least one UIObject which
 * will be rendered by the renderer.
 *
 * @author Saar ----
 * @version 1.2
 * @since 18.2.2018
 */
class UIBlock : Model, UIChildElement {

    override val style = Style(this)

    override var parent: UIElement = UINullElement

    var texture: ReadOnlyTexture? = null

    var discardMap: ReadOnlyTexture? = null

    fun inTouch(mx: Float, my: Float): Boolean {
        val radiuses = style.radiuses
        
        if (radiuses.isZero()) {
            return style.bounds.contains(mx, my)
        }

        val x = style.x.get().toFloat()
        val y = style.y.get().toFloat()
        val w = style.width.get().toFloat()
        val h = style.height.get().toFloat()

        val radius = radiuses.get(mx > x + w / 2, my < y + h / 2).toFloat()

        val bordersV = Rectangle(x + radius, y, w - radius * 2, h)
        val bordersH = Rectangle(x, y + radius, w, h - radius * 2)

        if (bordersV.contains(mx, my) || bordersH.contains(mx, my)) {
            return true
        }
        val cx = if (mx < x + w / 2) x + radius else x + w - radius

        val cy = if (my < y + h / 2) y + radius else y + h - radius

        val dx = cx - mx
        val dy = cy - my
        return radius * radius > dx * dx + dy * dy
    }

    override fun delete() {
        this.texture?.delete()
        this.discardMap?.delete()
    }

    override val mesh: Mesh get() = QuadMesh
}
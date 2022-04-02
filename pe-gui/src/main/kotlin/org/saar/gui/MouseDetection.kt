package org.saar.gui

import org.saar.maths.objects.Rectangle
import org.saar.maths.utils.Maths

object MouseDetection {

    fun contains(uiNode: UINode, x: Int, y: Int): Boolean {
        val nx = uiNode.style.position.getX().toFloat()
        val ny = uiNode.style.position.getY().toFloat()
        val nw = uiNode.style.width.get().toFloat()
        val nh = uiNode.style.height.get().toFloat()

        if (uiNode.style.radius.isZero()) {
            return Maths.isBetween(x.toFloat(), nx, nx + nw) &&
                    Maths.isBetween(y.toFloat(), ny, ny + nh)
        }

        val radius = uiNode.style.radius.get(
            x > nx + nw / 2,
            y < ny + nh / 2
        ).toFloat()

        val bordersV = Rectangle(nx + radius, ny, nw - radius * 2, nh)
        val bordersH = Rectangle(nx, ny + radius, nw, nh - radius * 2)

        if (bordersV.contains(x.toFloat(), y.toFloat()) ||
            bordersH.contains(x.toFloat(), y.toFloat())
        ) return true

        val cx = if (x < nx + nw / 2) nx + radius else nx + nw - radius

        val cy = if (y < ny + nh / 2) ny + radius else ny + nh - radius

        val dx = cx - x
        val dy = cy - y
        return radius * radius > dx * dx + dy * dy
    }
}

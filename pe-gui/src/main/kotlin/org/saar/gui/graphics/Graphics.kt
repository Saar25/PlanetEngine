package org.saar.gui.graphics

import org.saar.gui.style.Color
import org.saar.maths.objects.Polygon

interface Graphics {

    var color: Color

    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int)

    fun drawRectangle(x: Int, y: Int, w: Int, h: Int)

    fun fillRectangle(x: Int, y: Int, w: Int, h: Int)

    fun drawOval(cx: Int, cy: Int, a: Int, b: Int)

    fun fillOval(cx: Int, cy: Int, a: Int, b: Int)

    fun fillPolygon(polygon: Polygon)

    fun clear(clearColor: Color)

    fun process()

    fun delete()
}
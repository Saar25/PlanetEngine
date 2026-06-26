package org.saar.gui.graphics

import org.saar.gui.style.Color
import org.saar.gui.style.Colors
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.maths.objects.Polygon

class BufferedGraphics(private val texture: Texture2D) : Graphics {

    private val buffer = LwjglByteBuffer.allocate(4 * texture.width * texture.height)

    override var color: Color = Colors.BLACK

    private fun getPixel(x: Int, y: Int): Int {
        val position = x + y * this.texture.width
        var color = this.buffer[position].toInt()
        color = (color shl 4) + this.buffer[position + 1]
        color = (color shl 4) + this.buffer[position + 2]
        color = (color shl 4) + this.buffer[position + 3]
        return color
    }

    private fun setPixel(x: Int, y: Int, color: Color) {
        if (x >= 0 && x < this.texture.width && y >= 0 && y < this.texture.height) {
            this.buffer.position(x + y * this.texture.width)
            this.buffer.putInt(color.asInt())
        }
    }

    private fun minAndMax(a: Int, b: Int) = if (a < b) Pair(a, b) else Pair(b, a)

    override fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        val (xMin, xMax) = this.minAndMax(x1, x2)
        val (yMin, yMax) = this.minAndMax(y1, y2)
        val xLength = xMax - xMin
        val yLength = yMax - yMin

        if (xLength > yLength) {
            val m: Float = if (yLength == 0) 0f else xLength.toFloat() / yLength
            for (x in xMin..xMax) {
                this.setPixel(x, (yMin + (x - xMin) * m).toInt(), this.color)
            }
        } else {
            val m: Float = if (xLength == 0) 0f else yLength.toFloat() / xLength
            for (y in yMin..yMax) {
                this.setPixel((xMin + (y - yMin) * m).toInt(), y, this.color)
            }
        }
    }

    override fun drawRectangle(x: Int, y: Int, w: Int, h: Int) {
        drawLine(x, y, x + w, y)
        drawLine(x, y, x, y + h)
        drawLine(x + w, y, x + w, y + h)
        drawLine(x, y + h, x + w, y + h)
    }

    override fun fillRectangle(x: Int, y: Int, w: Int, h: Int) {
        for (i in 0 until w) {
            for (j in 0 until h) {
                setPixel(x + i, y + j, this.color)
            }
        }
    }

    override fun drawOval(cx: Int, cy: Int, a: Int, b: Int) {
        val invCx2 = 1f / (a * a)
        val invCy2 = 1f / (b * b)
        for (x in cx - a until cx + a * 2) {
            for (y in cy - b until cy + b * 2) {
                val dx = (x - cx).toFloat()
                val dy = (y - cy).toFloat()
                if (dx * dx * invCx2 + dy * dy * invCy2 == 1f) {
                    setPixel(x, y, this.color)
                }
            }
        }
    }

    override fun fillOval(cx: Int, cy: Int, a: Int, b: Int) {
        val invCx2 = 1f / (a * a)
        val invCy2 = 1f / (b * b)
        for (x in cx - a until cx + a * 2) {
            for (y in cy - b until cy + b * 2) {
                val dx = (x - cx).toFloat()
                val dy = (y - cy).toFloat()
                if (dx * dx * invCx2 + dy * dy * invCy2 <= 1) {
                    setPixel(x, y, this.color)
                }
            }
        }
    }

    override fun fillPolygon(polygon: Polygon) {}
    override fun clear(clearColor: Color) {
        this.buffer.clear()
        val color = clearColor.asInt()
        for (i in 0 until this.buffer.limit()) {
            this.buffer.putInt(color)
        }
    }

    override fun process() {
        this.buffer.flip().limit(this.buffer.capacity())
        this.texture.load(0, FormatType.RGBA, DataType.BYTE, this.buffer.asByteBuffer())
    }

    override fun delete() {
        this.buffer.close()
    }
}
package org.saar.gui.position

import org.saar.gui.position.coordinate.ReadonlyCoordinate
import org.saar.gui.position.length.ReadonlyLength
import org.saar.lwjgl.glfw.window.Window

class WindowPositioner(private val window: Window) : IPositioner {

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = ReadonlyLength { this.window.width }

    override val height: ReadonlyLength = ReadonlyLength { this.window.height }
}
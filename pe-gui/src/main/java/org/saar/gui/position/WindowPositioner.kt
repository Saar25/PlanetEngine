package org.saar.gui.position

import org.saar.gui.position.coordinate.Coordinate
import org.saar.gui.position.coordinate.Coordinates
import org.saar.gui.position.length.Length
import org.saar.gui.position.length.Lengths
import org.saar.lwjgl.glfw.window.Window

class WindowPositioner(private val window: Window) : IPositioner {

    override val x: Coordinate = Coordinates.Zero

    override val y: Coordinate = Coordinates.Zero

    override val width: Length = Lengths.WindowWidth(this.window)

    override val height: Length = Lengths.WindowHeight(this.window)
}
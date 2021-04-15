package org.saar.gui.position.length

import org.saar.gui.UIComponent
import org.saar.lwjgl.glfw.window.Window

object Lengths {

    class Width(private val container: UIComponent) : Length() {

        override fun get(): Int {
            val x = this.container.parent.positioner.x
            val w = this.container.parent.positioner.width
            return this.value.compute(x, w)
        }
    }

    class Height(private val container: UIComponent) : Length() {

        override fun get(): Int {
            val y = this.container.parent.positioner.y
            val h = this.container.parent.positioner.height
            return this.value.compute(y, h)
        }
    }

    class WindowWidth(private val window: Window) : Length() {

        override fun get() = this.window.width
    }

    class WindowHeight(private val window: Window) : Length() {

        override fun get() = this.window.height
    }
}
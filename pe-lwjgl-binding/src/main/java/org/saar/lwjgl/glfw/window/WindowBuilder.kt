package org.saar.lwjgl.glfw.window

class WindowBuilder(
    private val title: String,
    private val width: Int,
    private val height: Int,
    private val vSync: Boolean
) {
    private val hints = mutableListOf<WindowHint>()

    fun hint(hint: WindowHint): WindowBuilder {
        this.hints.add(hint)
        return this
    }

    fun build(): Window {
        this.hints.forEach(WindowHint::apply)

        return Window.create0(this.title, this.width, this.height, this.vSync)
    }
}

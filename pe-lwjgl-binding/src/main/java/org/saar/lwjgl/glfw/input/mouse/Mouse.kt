package org.saar.lwjgl.glfw.input.mouse

import org.lwjgl.glfw.GLFW
import org.saar.lwjgl.glfw.event.EventListener
import org.saar.lwjgl.glfw.event.EventListenersHelper
import org.saar.lwjgl.glfw.event.IntValueChange
import org.saar.lwjgl.glfw.event.OnAction
import org.saar.lwjgl.glfw.input.Modifiers

class Mouse(private val window: Long) {

    private var helperScroll = EventListenersHelper.empty<ScrollEvent>()

    private var helperClick = EventListenersHelper.empty<ClickEvent>()

    private var helperMove = EventListenersHelper.empty<MoveEvent>()

    var cursor: MouseCursor = MouseCursor.NORMAL
        set(cursor) {
            if (field != cursor) {
                GLFW.glfwSetInputMode(this.window, GLFW.GLFW_CURSOR, cursor.get())
                field = cursor
            }
        }

    var xPos: Int = 0
        private set
    var yPos: Int = 0
        private set
    private val scroll = 0.0

    init {
        init()
    }

    fun init() {
        GLFW.glfwSetMouseButtonCallback(
            this.window
        ) { window: Long, buttonId: Int, actionId: Int, mods: Int ->
            val button = MouseButton.valueOf(buttonId)
            val isDown = actionId == GLFW.GLFW_PRESS
            val modifiers = Modifiers(mods)

            val event = ClickEvent(this, button, isDown, modifiers)
            this.helperClick.fireEvent(event)
        }
        GLFW.glfwSetCursorPosCallback(this.window) { window: Long, xPos: Double, yPos: Double ->
            val event = MoveEvent(
                this,
                IntValueChange(this.xPos, xPos.toInt()),
                IntValueChange(this.yPos, yPos.toInt())
            )
            this.xPos = xPos.toInt()
            this.yPos = yPos.toInt()
            this.helperMove.fireEvent(event)
        }
        GLFW.glfwSetScrollCallback(this.window) { window: Long, xOffset: Double, yOffset: Double ->
            val event = ScrollEvent(this, yOffset)
            this.helperScroll.fireEvent(event)
        }
    }

    fun show() {
        this.cursor = MouseCursor.NORMAL
    }

    fun hide() {
        this.cursor = MouseCursor.DISABLED
    }

    fun isButtonDown(button: MouseButton): Boolean {
        return isState(button, MouseButtonState.PRESS)
    }

    fun isState(button: MouseButton, buttonState: MouseButtonState): Boolean {
        val state = getState(button)
        return state == buttonState.get()
    }

    fun getButtonState(button: MouseButton): MouseButtonState {
        val state = getState(button)
        return MouseButtonState.valueOf(state)
    }

    fun getState(button: MouseButton): Int {
        return GLFW.glfwGetMouseButton(this.window, button.get())
    }

    fun addScrollListener(listener: EventListener<ScrollEvent>) {
        this.helperScroll = this.helperScroll.addListener(listener)
    }

    fun removeScrollListener(listener: EventListener<ScrollEvent>) {
        this.helperScroll = this.helperScroll.removeListener(listener)
    }

    fun addClickListener(listener: EventListener<ClickEvent>) {
        this.helperClick = this.helperClick.addListener(listener)
    }

    fun removeClickListener(listener: EventListener<ClickEvent>) {
        this.helperClick = this.helperClick.removeListener(listener)
    }

    fun addMoveListener(listener: EventListener<MoveEvent>) {
        this.helperMove = this.helperMove.addListener(listener)
    }

    fun removeMoveListener(listener: EventListener<MoveEvent>) {
        this.helperMove = this.helperMove.removeListener(listener)
    }

    fun onClick(button: MouseButton): OnAction<ClickEvent> {
        return OnAction { listener ->
            addClickListener { e ->
                if (e.button == button) {
                    listener.onEvent(e)
                }
            }
        }
    }
}

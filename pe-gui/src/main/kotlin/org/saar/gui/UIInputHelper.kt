package org.saar.gui

import org.saar.gui.event.EventListener
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent

class UIInputHelper(private val container: UINode) {

    var isMouseOver: Boolean = false
        private set

    var isMousePressed: Boolean = false
        private set

    var isFocused: Boolean
        get() = UIActiveElement.current == this.container
        private set(value) {
            if (this.isFocused != value) {
                UIActiveElement.current = if (value) this.container else UINullNode
            }
        }

    private val mouseEnterEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val mouseExitEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val mousePressEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val mouseReleaseEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val mouseMoveEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val mouseDragEventListeners = mutableListOf<EventListener<MouseEvent>>()

    private val keyPressEventListeners = mutableListOf<EventListener<KeyboardEvent>>()

    private val keyReleaseEventListeners = mutableListOf<EventListener<KeyboardEvent>>()

    private val keyRepeatEventListeners = mutableListOf<EventListener<KeyboardEvent>>()

    fun onMouseMoveEvent(event: MouseEvent, inside: Boolean) {
        if (inside && !this.isMouseOver) {
            this.isMouseOver = true
            this.mouseEnterEventListeners.forEach { it.handle(event) }
        } else if (!inside && this.isMouseOver) {
            this.isMouseOver = false
            this.mouseExitEventListeners.forEach { it.handle(event) }
        }

        if (inside && !event.button.isPrimary) {
            this.mouseMoveEventListeners.forEach { it.handle(event) }
        } else if (event.button.isPrimary && this.isMousePressed) {
            this.mouseDragEventListeners.forEach { it.handle(event) }
        }
    }

    fun onMousePressEvent(event: MouseEvent): Boolean {
        if (this.isMouseOver) {
            this.isMousePressed = true
            this.mousePressEventListeners.forEach { it.handle(event) }
            this.isFocused = true
            return true
        }
        this.isFocused = false
        return false
    }

    fun onMouseReleaseEvent(event: MouseEvent): Boolean {
        if (this.isMousePressed) {
            this.isMousePressed = false
            this.isFocused = true
            this.mouseReleaseEventListeners.forEach { it.handle(event) }
            return true
        }
        this.isFocused = false
        return false
    }

    fun onKeyPressEvent(event: KeyboardEvent) {
        if (this.isFocused) this.keyPressEventListeners.forEach { it.handle(event) }
    }

    fun onKeyReleaseEvent(event: KeyboardEvent) {
        if (this.isFocused) this.keyReleaseEventListeners.forEach { it.handle(event) }
    }

    fun onKeyRepeatEvent(event: KeyboardEvent) {
        if (this.isFocused) this.keyRepeatEventListeners.forEach { it.handle(event) }
    }

    fun addMouseEnterEventListener(listener: EventListener<MouseEvent>) {
        this.mouseEnterEventListeners += listener
    }

    fun addMouseExitEventListener(listener: EventListener<MouseEvent>) {
        this.mouseExitEventListeners += listener
    }

    fun addMousePressEventListener(listener: EventListener<MouseEvent>) {
        this.mousePressEventListeners += listener
    }

    fun addMouseReleaseEventListener(listener: EventListener<MouseEvent>) {
        this.mouseReleaseEventListeners += listener
    }

    fun addMouseMoveEventListener(listener: EventListener<MouseEvent>) {
        this.mouseMoveEventListeners += listener
    }

    fun addMouseDragEventListener(listener: EventListener<MouseEvent>) {
        this.mouseDragEventListeners += listener
    }

    fun addKeyPressEventListener(listener: EventListener<KeyboardEvent>) {
        this.keyPressEventListeners += listener
    }

    fun addKeyReleaseEventListener(listener: EventListener<KeyboardEvent>) {
        this.keyReleaseEventListeners += listener
    }

    fun addKeyRepeatEventListener(listener: EventListener<KeyboardEvent>) {
        this.keyRepeatEventListeners += listener
    }
}
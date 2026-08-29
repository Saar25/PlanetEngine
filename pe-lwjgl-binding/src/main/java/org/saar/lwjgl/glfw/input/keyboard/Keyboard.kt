package org.saar.lwjgl.glfw.input.keyboard

import org.lwjgl.glfw.GLFW
import org.saar.lwjgl.glfw.event.EventListener
import org.saar.lwjgl.glfw.event.EventListenersHelper
import org.saar.lwjgl.glfw.event.OnAction
import org.saar.lwjgl.glfw.input.Modifiers
import org.saar.lwjgl.glfw.input.keyboard.KeyMapper.mapToKey
import org.saar.lwjgl.glfw.input.keyboard.KeyState.Companion.valueOf

class Keyboard(private val window: Long) {

    private var helperKeyPress = EventListenersHelper.empty<KeyEvent>()

    private var helperKeyRelease = EventListenersHelper.empty<KeyEvent>()

    private var helperKeyRepeat = EventListenersHelper.empty<KeyEvent>()

    init {
        this.init()
    }

    fun init() {
        GLFW.glfwSetKeyCallback(this.window) { window: Long, code: Int, scanCode: Int, action: Int, mods: Int ->
            val modifiers = Modifiers(mods)
            val key = mapToKey(code, modifiers)
            val event = KeyEvent(code, modifiers, key)
            if (action == KeyState.PRESS.get()) {
                this.helperKeyPress.fireEvent(event)
            } else if (action == KeyState.RELEASE.get()) {
                this.helperKeyRelease.fireEvent(event)
            } else if (action == KeyState.REPEAT.get()) {
                this.helperKeyRepeat.fireEvent(event)
            }
        }
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return this.isKeyState(keyCode, KeyState.PRESS)
    }

    fun isKeyState(keyCode: Int, keyState: KeyState): Boolean {
        val state = this.getState(keyCode)
        return state == keyState.get()
    }

    fun getKeyState(keyCode: Int): KeyState {
        val state = this.getState(keyCode)
        return valueOf(state)
    }

    fun getState(keyCode: Int): Int {
        return GLFW.glfwGetKey(this.window, keyCode)
    }

    fun allKeysPressed(vararg keyCodes: Int): Boolean {
        for (keyCode in keyCodes) {
            if (!this.isKeyPressed(keyCode)) {
                return false
            }
        }
        return true
    }

    fun anyKeyPressed(vararg keyCodes: Int): Boolean {
        for (keyCode in keyCodes) {
            if (this.isKeyPressed(keyCode)) {
                return true
            }
        }
        return false
    }

    fun addKeyPressListener(listener: EventListener<KeyEvent>) {
        this.helperKeyPress = this.helperKeyPress.addListener(listener)
    }

    fun addKeyReleaseListener(listener: EventListener<KeyEvent>) {
        this.helperKeyRelease = this.helperKeyRelease.addListener(listener)
    }

    fun addKeyRepeatListener(listener: EventListener<KeyEvent>) {
        this.helperKeyRepeat = this.helperKeyRepeat.addListener(listener)
    }

    fun removeKeyPressListener(listener: EventListener<KeyEvent>) {
        this.helperKeyPress = this.helperKeyPress.removeListener(listener)
    }

    fun removeKeyReleaseListener(listener: EventListener<KeyEvent>) {
        this.helperKeyRelease = this.helperKeyRelease.removeListener(listener)
    }

    fun removeKeyRepeatListener(listener: EventListener<KeyEvent>) {
        this.helperKeyRepeat = this.helperKeyRepeat.removeListener(listener)
    }

    fun onKeyPress(keyChar: Char): OnAction<KeyEvent> {
        return this.onKeyPress(keyChar.code)
    }

    fun onKeyPress(keyCode: Int): OnAction<KeyEvent> {
        return OnAction { listener ->
            this.addKeyPressListener { e ->
                if (e.code == keyCode) {
                    listener.onEvent(e)
                }
            }
        }
    }

    fun onKeyRepeat(keyCode: Int): OnAction<KeyEvent> {
        return OnAction { listener: EventListener<KeyEvent> ->
            this.addKeyRepeatListener { e: KeyEvent ->
                if (e.code == keyCode) {
                    listener.onEvent(e)
                }
            }
        }
    }

    fun onKeyRelease(keyCode: Int): OnAction<KeyEvent> {
        return OnAction { listener: EventListener<KeyEvent> ->
            this.addKeyReleaseListener { e: KeyEvent ->
                if (e.code == keyCode) {
                    listener.onEvent(e)
                }
            }
        }
    }
}

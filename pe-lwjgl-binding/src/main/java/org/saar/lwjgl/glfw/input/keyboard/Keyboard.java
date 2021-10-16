package org.saar.lwjgl.glfw.input.keyboard;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.event.EventListener;
import org.saar.lwjgl.glfw.event.EventListenersHelper;
import org.saar.lwjgl.glfw.event.OnAction;
import org.saar.lwjgl.glfw.input.Modifiers;

public class Keyboard {

    private final long window;

    private EventListenersHelper<KeyEvent> helperKeyPress = EventListenersHelper.empty();

    private EventListenersHelper<KeyEvent> helperKeyRelease = EventListenersHelper.empty();

    private EventListenersHelper<KeyEvent> helperKeyRepeat = EventListenersHelper.empty();

    public Keyboard(long window) {
        this.window = window;
        init();
    }

    public void init() {
        GLFW.glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            final KeyEvent event = new KeyEvent(key, new Modifiers(mods));
            if (action == KeyState.PRESS.get()) {
                this.helperKeyPress.fireEvent(event);
            } else if (action == KeyState.RELEASE.get()) {
                this.helperKeyRelease.fireEvent(event);
            } else if (action == KeyState.REPEAT.get()) {
                this.helperKeyRepeat.fireEvent(event);
            }
        });
    }

    public boolean isKeyPressed(int keyCode) {
        return isKeyState(keyCode, KeyState.PRESS);
    }

    public boolean isKeyState(int keyCode, KeyState keyState) {
        final int state = getState(keyCode);
        return state == keyState.get();
    }

    public KeyState getKeyState(int keyCode) {
        final int state = getState(keyCode);
        return KeyState.valueOf(state);
    }

    public int getState(int keyCode) {
        return GLFW.glfwGetKey(this.window, keyCode);
    }

    public boolean allKeysPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (!isKeyPressed(keyCode)) {
                return false;
            }
        }
        return true;
    }

    public boolean anyKeyPressed(int... keyCodes) {
        for (int keyCode : keyCodes) {
            if (isKeyPressed(keyCode)) {
                return true;
            }
        }
        return false;
    }

    public void addKeyPressListener(EventListener<KeyEvent> listener) {
        this.helperKeyPress = this.helperKeyPress.addListener(listener);
    }

    public void addKeyReleaseListener(EventListener<KeyEvent> listener) {
        this.helperKeyRelease = this.helperKeyRelease.addListener(listener);
    }

    public void addKeyRepeatListener(EventListener<KeyEvent> listener) {
        this.helperKeyRepeat = this.helperKeyRepeat.addListener(listener);
    }

    public void removeKeyPressListener(EventListener<KeyEvent> listener) {
        this.helperKeyPress = this.helperKeyPress.removeListener(listener);
    }

    public void removeKeyReleaseListener(EventListener<KeyEvent> listener) {
        this.helperKeyRelease = this.helperKeyRelease.removeListener(listener);
    }

    public void removeKeyRepeatListener(EventListener<KeyEvent> listener) {
        this.helperKeyRepeat = this.helperKeyRepeat.removeListener(listener);
    }

    public OnAction<KeyEvent> onKeyPress(char keyChar) {
        return onKeyPress((int) keyChar);
    }

    public OnAction<KeyEvent> onKeyPress(int keyCode) {
        return listener -> addKeyPressListener(e -> {
            if (e.getKeyCode() == keyCode) {
                listener.onEvent(e);
            }
        });
    }

    public OnAction<KeyEvent> onKeyRelease(int keyCode) {
        return listener -> addKeyReleaseListener(e -> {
            if (e.getKeyCode() == keyCode) {
                listener.onEvent(e);
            }
        });
    }
}

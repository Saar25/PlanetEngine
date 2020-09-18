package org.saar.lwjgl.glfw.input.keyboard;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.input.EventListener;
import org.saar.lwjgl.glfw.input.EventListenersHelper;
import org.saar.lwjgl.glfw.input.OnAction;

public class Keyboard {

    private final long window;

    private EventListenersHelper<KeyPressEvent> helperKeyPress = EventListenersHelper.empty();

    private EventListenersHelper<KeyReleaseEvent> helperKeyRelease = EventListenersHelper.empty();

    public Keyboard(long window) {
        this.window = window;
        init();
    }

    public void init() {
        GLFW.glfwSetKeyCallback(window, (window, key, scanCode, action, mods) -> {
            if (action == KeyState.PRESS.get()) {
                final KeyPressEvent event = new KeyPressEvent(key);
                this.helperKeyPress.fireEvent(event);
            } else if (action == KeyState.RELEASE.get()) {
                final KeyReleaseEvent event = new KeyReleaseEvent(key);
                this.helperKeyRelease.fireEvent(event);
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

    public void addKeyPressListener(EventListener<KeyPressEvent> listener) {
        this.helperKeyPress = this.helperKeyPress.addListener(listener);
    }

    public void addKeyReleaseListener(EventListener<KeyReleaseEvent> listener) {
        this.helperKeyRelease = this.helperKeyRelease.addListener(listener);
    }

    public void removeKeyPressListener(EventListener<KeyPressEvent> listener) {
        this.helperKeyPress = this.helperKeyPress.removeListener(listener);
    }

    public void removeKeyReleaseListener(EventListener<KeyReleaseEvent> listener) {
        this.helperKeyRelease = this.helperKeyRelease.removeListener(listener);
    }

    public OnAction<KeyPressEvent> onKeyPress(char keyChar) {
        return onKeyPress((int) keyChar);
    }

    public OnAction<KeyPressEvent> onKeyPress(int keyCode) {
        return listener -> addKeyPressListener(e -> {
            if (e.getKeyCode() == keyCode) {
                listener.onEvent(e);
            }
        });
    }

    public OnAction<KeyReleaseEvent> onKeyRelease(int keyCode) {
        return listener -> addKeyReleaseListener(e -> {
            if (e.getKeyCode() == keyCode) {
                listener.onEvent(e);
            }
        });
    }
}

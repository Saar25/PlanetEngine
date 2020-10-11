package org.saar.lwjgl.glfw.window.hint;

import org.saar.lwjgl.glfw.window.WindowHint;
import org.saar.lwjgl.glfw.window.WindowHintType;

public class WindowHintOpenGlForwardCompatibility extends WindowHintBoolean implements WindowHint {

    private final boolean value;

    public WindowHintOpenGlForwardCompatibility(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public WindowHintType getType() {
        return WindowHintType.OPENGL_FORWARD_COMPAT;
    }
}

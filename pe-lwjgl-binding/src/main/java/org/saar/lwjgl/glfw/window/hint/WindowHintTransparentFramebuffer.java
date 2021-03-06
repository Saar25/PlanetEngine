package org.saar.lwjgl.glfw.window.hint;

import org.saar.lwjgl.glfw.window.WindowHint;
import org.saar.lwjgl.glfw.window.WindowHintType;

public class WindowHintTransparentFramebuffer extends WindowHintBoolean implements WindowHint {

    private final boolean value;

    public WindowHintTransparentFramebuffer(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public WindowHintType getType() {
        return WindowHintType.TRANSPARENT_FRAMEBUFFER;
    }
}

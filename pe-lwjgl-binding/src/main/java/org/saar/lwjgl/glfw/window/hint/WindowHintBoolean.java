package org.saar.lwjgl.glfw.window.hint;

import org.saar.lwjgl.glfw.window.WindowHint;

public abstract class WindowHintBoolean extends WindowHintBase implements WindowHint {

    @Override
    public int getValue() {
        return getBooleanValue() ? 1 : 0;
    }

    public abstract boolean getBooleanValue();
}

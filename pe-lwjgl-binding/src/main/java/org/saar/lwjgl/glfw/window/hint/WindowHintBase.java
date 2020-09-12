package org.saar.lwjgl.glfw.window.hint;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.window.WindowHint;
import org.saar.lwjgl.glfw.window.WindowHintType;

public abstract class WindowHintBase implements WindowHint {

    public abstract int getValue();

    public abstract WindowHintType getType();

    @Override
    public void apply() {
        final int value = getValue();
        final WindowHintType type = getType();
        GLFW.glfwWindowHint(type.get(), value);
    }
}

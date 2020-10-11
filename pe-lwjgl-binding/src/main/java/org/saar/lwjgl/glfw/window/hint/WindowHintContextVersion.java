package org.saar.lwjgl.glfw.window.hint;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.window.WindowHint;
import org.saar.lwjgl.glfw.window.WindowHintType;

public class WindowHintContextVersion implements WindowHint {

    private final int major;
    private final int minor;

    public WindowHintContextVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public void apply() {
        GLFW.glfwWindowHint(WindowHintType.CONTEXT_VERSION_MAJOR.get(), this.major);
        GLFW.glfwWindowHint(WindowHintType.CONTEXT_VERSION_MINOR.get(), this.minor);
    }
}

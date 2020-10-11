package org.saar.lwjgl.glfw.window.hint;

import org.lwjgl.glfw.GLFW;

public enum OpenGlProfileType {

    ANY(GLFW.GLFW_OPENGL_ANY_PROFILE),
    CORE(GLFW.GLFW_OPENGL_CORE_PROFILE),
    COMPATIBILITY(GLFW.GLFW_OPENGL_COMPAT_PROFILE),
    ;

    private final int value;

    OpenGlProfileType(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }
}

package org.saar.lwjgl.glfw.window.hint;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.window.WindowHint;
import org.saar.lwjgl.glfw.window.WindowHintType;

public class WindowHintOpenGlProfile implements WindowHint {

    private final OpenGlProfileType value;

    public WindowHintOpenGlProfile(OpenGlProfileType value) {
        this.value = value;
    }

    public static WindowHintOpenGlProfile any() {
        return new WindowHintOpenGlProfile(OpenGlProfileType.ANY);
    }

    public static WindowHintOpenGlProfile core() {
        return new WindowHintOpenGlProfile(OpenGlProfileType.CORE);
    }

    public static WindowHintOpenGlProfile compact() {
        return new WindowHintOpenGlProfile(OpenGlProfileType.COMPATIBILITY);
    }

    @Override
    public void apply() {
        GLFW.glfwWindowHint(WindowHintType.OPENGL_PROFILE.get(), this.value.get());
    }
}

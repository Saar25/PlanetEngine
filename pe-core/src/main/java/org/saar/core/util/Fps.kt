package org.saar.core.util;

import org.lwjgl.glfw.GLFW;

public class Fps {

    private double last;

    public Fps() {
        this.last = current();
    }

    private static double current() {
        return GLFW.glfwGetTime();
    }

    public void update() {
        this.last = current();
    }

    public double delta() {
        return current() - this.last;
    }

    public double fps() {
        return 1 / delta();
    }
}

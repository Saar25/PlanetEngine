package org.saar.lwjgl.glfw.window;

import org.joml.primitives.Rectanglei;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.saar.maths.objects.Dimensions;

import java.nio.IntBuffer;
import java.util.Objects;

public class Monitor {

    public static final Monitor primary = new Monitor(GLFW.glfwGetPrimaryMonitor());

    final long id;

    private final Dimensions dimensions;
    private final Rectanglei workArea;

    private Monitor(long id) {
        this.id = id;
        this.dimensions = findDimensions();
        this.workArea = findWorkArea();
    }

    private Rectanglei findWorkArea() {
        try (final MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer x = stack.callocInt(1);
            final IntBuffer y = stack.callocInt(1);
            final IntBuffer w = stack.callocInt(1);
            final IntBuffer h = stack.callocInt(1);
            GLFW.glfwGetMonitorWorkarea(this.id, x, y, w, h);

            return new Rectanglei(x.get(), y.get(), w.get(), h.get());
        }
    }

    private Dimensions findDimensions() {
        final GLFWVidMode vidMode = Objects.requireNonNull(GLFW.glfwGetVideoMode(this.id));
        return new Dimensions(vidMode.width(), vidMode.height());
    }

    public Dimensions getDimensions() {
        return this.dimensions;
    }

    public Rectanglei getWorkArea() {
        return this.workArea;
    }
}

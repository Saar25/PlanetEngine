package org.saar.lwjgl.glfw.window;

import org.saar.lwjgl.glfw.event.IntValueChange;
import org.saar.lwjgl.glfw.event.Event;

public class ResizeEvent extends Event {

    private final IntValueChange width;
    private final IntValueChange height;

    public ResizeEvent(IntValueChange width, IntValueChange height) {
        this.width = width;
        this.height = height;
    }

    public IntValueChange getWidth() {
        return this.width;
    }

    public IntValueChange getHeight() {
        return this.height;
    }
}

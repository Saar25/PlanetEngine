package org.saar.lwjgl.glfw.window;

import java.util.ArrayList;
import java.util.List;

public class WindowBuilder {

    private final List<WindowHint> hints = new ArrayList<>();

    private final String title;
    private final int width;
    private final int height;
    private final boolean vSync;

    public WindowBuilder(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }

    public WindowBuilder hint(WindowHint hint) {
        this.hints.add(hint);
        return this;
    }

    public Window build() {
        for (WindowHint hint : this.hints) {
            hint.apply();
        }
        return Window.create0(this.title, this.width, this.height, this.vSync);
    }
}

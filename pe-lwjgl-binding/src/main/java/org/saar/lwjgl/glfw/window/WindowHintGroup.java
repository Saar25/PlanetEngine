package org.saar.lwjgl.glfw.window;

import java.util.EnumMap;
import java.util.Map;

public class WindowHintGroup {

    private final Map<WindowHint, Integer> hints = new EnumMap<>(WindowHint.class);

    public WindowHintGroup setHint(WindowHint hint, int value) {
        getHints().put(hint, value);
        return this;
    }

    public WindowHintGroup setHint(WindowHint hint, boolean value) {
        getHints().put(hint, value ? 1 : 0);
        return this;
    }

    public Map<WindowHint, Integer> getHints() {
        return this.hints;
    }
}

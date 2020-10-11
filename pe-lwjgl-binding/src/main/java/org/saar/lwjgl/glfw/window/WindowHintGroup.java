package org.saar.lwjgl.glfw.window;

import java.util.EnumMap;
import java.util.Map;

public class WindowHintGroup {

    private final Map<WindowHintType, Integer> hints = new EnumMap<>(WindowHintType.class);

    public WindowHintGroup setHint(WindowHintType hint, int value) {
        getHints().put(hint, value);
        return this;
    }

    public WindowHintGroup setHint(WindowHintType hint, boolean value) {
        getHints().put(hint, value ? 1 : 0);
        return this;
    }

    public Map<WindowHintType, Integer> getHints() {
        return this.hints;
    }
}

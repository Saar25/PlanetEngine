package org.saar.lwjgl.glfw.input;

import org.saar.lwjgl.glfw.input.event.Event;
import org.saar.lwjgl.glfw.input.event.EventListener;

public interface OnAction<T extends Event> {

    void perform(EventListener<T> listener);

}

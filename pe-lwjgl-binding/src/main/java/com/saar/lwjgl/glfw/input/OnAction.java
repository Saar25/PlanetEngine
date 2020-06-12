package com.saar.lwjgl.glfw.input;

import com.saar.lwjgl.glfw.input.event.Event;
import com.saar.lwjgl.glfw.input.event.EventListener;

public interface OnAction<T extends Event> {

    void perform(EventListener<T> listener);

}

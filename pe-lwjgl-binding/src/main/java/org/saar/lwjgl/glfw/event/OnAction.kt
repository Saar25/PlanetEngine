package org.saar.lwjgl.glfw.event;

public interface OnAction<T extends Event> {

    void perform(EventListener<T> listener);

}

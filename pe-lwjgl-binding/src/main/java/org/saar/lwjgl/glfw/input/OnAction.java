package org.saar.lwjgl.glfw.input;

public interface OnAction<T extends Event> {

    void perform(EventListener<T> listener);

}

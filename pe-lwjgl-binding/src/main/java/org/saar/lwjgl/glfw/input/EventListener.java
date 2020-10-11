package org.saar.lwjgl.glfw.input;

public interface EventListener<T extends Event> {

    void onEvent(T e);

}

package org.saar.lwjgl.glfw.event;

public interface EventListener<T extends Event> {

    void onEvent(T e);

}

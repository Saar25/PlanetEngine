package org.saar.lwjgl.opengl.shaders;

public class RenderState<T> {

    private final T instance;
    private final Camera camera;

    public RenderState(Camera camera, T instance) {
        this.instance = instance;
        this.camera = camera;
    }

    public RenderState(Camera camera) {
        this.instance = null;
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public T getInstance() {
        return instance;
    }
}

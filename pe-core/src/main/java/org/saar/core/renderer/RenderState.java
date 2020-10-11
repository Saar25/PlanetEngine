package org.saar.core.renderer;

public class RenderState<T> {

    private final T instance;

    public RenderState(T instance) {
        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }
}

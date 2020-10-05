package org.saar.core.renderer;

public class InstanceRenderState<T> {

    private final T instance;

    public InstanceRenderState(T instance) {
        this.instance = instance;
    }

    public T getInstance() {
        return instance;
    }
}

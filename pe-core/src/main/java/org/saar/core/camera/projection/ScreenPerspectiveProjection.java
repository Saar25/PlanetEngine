package org.saar.core.camera.projection;

import org.saar.core.camera.Projection;
import org.saar.core.screen.Screen;

public class ScreenPerspectiveProjection extends PerspectiveProjection implements Projection {

    private final Screen screen;
    private float fov;
    private float near;
    private float far;

    public ScreenPerspectiveProjection(Screen screen, float fov, float near, float far) {
        this.screen = screen;
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    @Override
    public float getFov() {
        return this.fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    @Override
    public float getWidth() {
        return this.screen.getWidth();
    }

    @Override
    public float getHeight() {
        return this.screen.getHeight();
    }

    @Override
    public float getNear() {
        return this.near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    @Override
    public float getFar() {
        return this.far;
    }

    public void setFar(float far) {
        this.far = far;
    }
}

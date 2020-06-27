package org.saar.lwjgl.glfw.window;

public class WindowBuilder {

    private final WindowHintGroup hints = new WindowHintGroup();

    public WindowBuilder setFocused(boolean value) {
        this.hints.setHint(WindowHint.FOCUSED, value);
        return this;
    }

    public WindowBuilder setIconified(boolean value) {
        this.hints.setHint(WindowHint.ICONIFIED, value);
        return this;
    }

    public WindowBuilder setResizeable(boolean value) {
        this.hints.setHint(WindowHint.RESIZABLE, value);
        return this;
    }

    public WindowBuilder setVisible(boolean value) {
        this.hints.setHint(WindowHint.VISIBLE, value);
        return this;
    }

    public WindowBuilder setDecorated(boolean value) {
        this.hints.setHint(WindowHint.DECORATED, value);
        return this;
    }

    public WindowBuilder setAutoIconify(boolean value) {
        this.hints.setHint(WindowHint.AUTO_ICONIFY, value);
        return this;
    }

    public WindowBuilder setFloating(boolean value) {
        this.hints.setHint(WindowHint.FLOATING, value);
        return this;
    }

    public WindowBuilder setMaximized(boolean value) {
        this.hints.setHint(WindowHint.MAXIMIZED, value);
        return this;
    }

    public WindowBuilder setCenterCursor(boolean value) {
        this.hints.setHint(WindowHint.CENTER_CURSOR, value);
        return this;
    }

    public WindowBuilder setTransparentFrameBuffer(boolean value) {
        this.hints.setHint(WindowHint.TRANSPARENT_FRAMEBUFFER, value);
        return this;
    }

    public WindowBuilder setHovered(boolean value) {
        this.hints.setHint(WindowHint.HOVERED, value);
        return this;
    }

    public WindowBuilder setFocusOnShow(boolean value) {
        this.hints.setHint(WindowHint.FOCUS_ON_SHOW, value);
        return this;
    }

    public WindowBuilder setClientApi(int value) {
        this.hints.setHint(WindowHint.CLIENT_API, value);
        return this;
    }

    public WindowBuilder setContextVersion(int major, int minor) {
        this.hints.setHint(WindowHint.CONTEXT_VERSION_MAJOR, major);
        this.hints.setHint(WindowHint.CONTEXT_VERSION_MINOR, minor);
        return this;
    }

    public WindowBuilder setContextRevision(int value) {
        this.hints.setHint(WindowHint.CONTEXT_REVISION, value);
        return this;
    }

    public WindowBuilder setContextRobustness(int value) {
        this.hints.setHint(WindowHint.CONTEXT_ROBUSTNESS, value);
        return this;
    }

    public WindowBuilder setOpenGLForwardCompatibility(boolean value) {
        this.hints.setHint(WindowHint.OPENGL_FORWARD_COMPAT, value);
        return this;
    }

    public WindowBuilder setOpenGLDebugContext(int value) {
        this.hints.setHint(WindowHint.OPENGL_DEBUG_CONTEXT, value);
        return this;
    }

    public WindowBuilder setOpenGLProfile(int value) {
        this.hints.setHint(WindowHint.OPENGL_PROFILE, value);
        return this;
    }

    public WindowBuilder setContextReleaseBehavior(int value) {
        this.hints.setHint(WindowHint.CONTEXT_RELEASE_BEHAVIOR, value);
        return this;
    }

    public WindowBuilder setContextNoError(int value) {
        this.hints.setHint(WindowHint.CONTEXT_NO_ERROR, value);
        return this;
    }

    public WindowBuilder setContextCreationApi(int value) {
        this.hints.setHint(WindowHint.CONTEXT_CREATION_API, value);
        return this;
    }

    public WindowBuilder setScaleToMonitor(int value) {
        this.hints.setHint(WindowHint.SCALE_TO_MONITOR, value);
        return this;
    }

}

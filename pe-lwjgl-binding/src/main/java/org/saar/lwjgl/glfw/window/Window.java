package org.saar.lwjgl.glfw.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.glfw.window.hint.*;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class Window {

    private static Window current = null;

    private final int[] xBuffer = new int[1];
    private final int[] yBuffer = new int[1];

    private String title;
    private long id;
    private int width;
    private int height;

    private Mouse mouse;
    private Keyboard keyboard;

    private boolean resized;
    private boolean vSync;

    public Window(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.resized = false;
        Window.current = this;
    }

    public static WindowBuilder builder() {
        final WindowBuilder windowBuilder = new WindowBuilder();
        windowBuilder.setVisible(false);
        windowBuilder.setResizeable(true);
        windowBuilder.setContextVersion(3, 2);
        windowBuilder.setOpenGLProfile(GLFW.GLFW_OPENGL_CORE_PROFILE);
        windowBuilder.setOpenGLForwardCompatibility(true);
        final WindowHint[] hints = {
                new WindowHintVisible(false),
                new WindowHintResizeable(true),
                new WindowHintContextVersion(3, 2),
                new WindowHintOpenGlProfile(OpenGlProfileType.CORE),
                new WindowHintOpenGlForwardCompatibility(true)
        };
        return windowBuilder;
    }

    public static Window create(String title, int width, int height, boolean vSync) {
        return new Window(title, width, height, vSync);
    }

    /**
     * Returns the current focused window
     *
     * @return the current window
     */
    public static Window current() {
        return current;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        setHint(WindowHintType.CONTEXT_VERSION_MAJOR, 3);
        setHint(WindowHintType.CONTEXT_VERSION_MINOR, 2);
        setHint(WindowHintType.OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        setHint(WindowHintType.OPENGL_FORWARD_COMPAT, true);

        // Create the window
        id = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (this.id == 0) {
            throw new RuntimeException("Failed to init the GLFW window");
        }

        GLFW.glfwSetFramebufferSizeCallback(this.id, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });

        center();

        makeContextCurrent();

        if (isvSync()) {
            // Enable v-sync
            GLFW.glfwSwapInterval(1);
        }

        // Make the window visible
        setVisible(true);

        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback(System.err);

        this.mouse = new Mouse(this.id);
        this.keyboard = new Keyboard(this.id);
    }

    private static void setHint(WindowHintType hint, int value) {
        GLFW.glfwWindowHint(hint.get(), value);
    }

    private static void setHint(WindowHintType hint, boolean value) {
        GLFW.glfwWindowHint(hint.get(), value ? 1 : 0);
    }

    private void makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(this.id);
    }

    /**
     * Center the window in the middle of the screen
     */
    public void center() {
        final long monitor = GLFW.glfwGetPrimaryMonitor();
        final GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
        if (vidMode != null) {
            final int w = (vidMode.width() - getWidth()) / 2;
            final int h = (vidMode.height() - getHeight()) / 2;
            this.setPosition(w, h);
        }
    }

    /**
     * Sets the window visibility
     *
     * @param visible true if the window should be visible, false otherwise
     */
    public void setVisible(boolean visible) {
        if (visible) show();
        else hide();
    }

    /**
     * Sets the window invisible
     */
    private void show() {
        GLFW.glfwShowWindow(this.id);
    }

    /**
     * Sets the window visible
     */
    private void hide() {
        GLFW.glfwHideWindow(this.id);
    }

    /**
     * Sets the window visible
     */
    private void setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.id, title);
    }

    /**
     * Sets the position of the window
     *
     * @param x the x position of the window
     * @param y the y position of the window
     */
    public void setPosition(int x, int y) {
        GLFW.glfwSetWindowPos(this.id, x, y);
    }

    /**
     * Sets the size of the window
     *
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(this.id, width, height);
    }

    /**
     * Returns whether the window has been closed by the user
     *
     * @return true if window has been close else false
     */
    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(this.id);
    }

    /**
     * Returns whether the window has been closed by the user
     *
     * @return true if window has been close else false
     */
    public boolean isOpen() {
        return !GLFW.glfwWindowShouldClose(this.id);
    }

    /**
     * Sets the window should close flag. Used for closing up the program
     *
     * @param shouldClose true if wants the window to close else false
     */
    public void setWindowShouldClose(boolean shouldClose) {
        GLFW.glfwSetWindowShouldClose(this.id, shouldClose);
    }

    /**
     * Swap the buffers of the window
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.id);
    }

    /**
     * Poll glfw events
     */
    public void pollEvents() {
        GLFW.glfwPollEvents();
    }

    /**
     * wait for glfw events
     */
    public void waitEvents() {
        GLFW.glfwWaitEvents();
    }

    /**
     * Return the window's x position
     *
     * @return the window's x position
     */
    public int getX() {
        GLFW.glfwGetWindowPos(this.id, xBuffer, yBuffer);
        return this.xBuffer[0];
    }

    /**
     * Return the window's y position
     *
     * @return the window's y position
     */
    public int getY() {
        GLFW.glfwGetWindowPos(this.id, xBuffer, yBuffer);
        return this.yBuffer[0];
    }

    /**
     * Creates the mouse that corresponds to this window
     *
     * @return the mouse
     */
    public Mouse getMouse() {
        return this.mouse;
    }

    /**
     * Returns the keyboard that corresponds to this window
     *
     * @return the keyboard
     */
    public Keyboard getKeyboard() {
        return this.keyboard;
    }

    /**
     * Returns the window's title
     *
     * @return the window's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Return the window's width
     *
     * @return the window's width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the window's height
     *
     * @return the window's height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns whether the window is vSync
     *
     * @return true if window is vSync else false
     */
    public boolean isvSync() {
        return this.vSync;
    }

    /**
     * Returns whether the window has been resized by the user since last updated
     *
     * @return true if window has been resized else false
     */
    public boolean isResized() {
        return this.resized;
    }

    /**
     * Updates the window
     */
    public void update(boolean swapBuffers) {
        if (swapBuffers) {
            swapBuffers();
        }
        resized = false;
        Window.current = this;
        GlUtils.setViewport(0, 0, getWidth(), getHeight());
    }

    /**
     * Destroy the window and free all resources allocated in its context
     */
    public void destroy() {
        GLFW.glfwDestroyWindow(this.id);
    }
}

package org.saar.lwjgl.glfw.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.saar.lwjgl.glfw.input.Keyboard;
import org.saar.lwjgl.glfw.input.Mouse;
import org.saar.lwjgl.opengl.utils.GlUtils;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.IntBuffer;

public class Window {

    private static Window current = null;

    private final IntBuffer xPos = MemoryUtils.allocInt(1);
    private final IntBuffer yPos = MemoryUtils.allocInt(1);

    private final String title;
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
        setHint(WindowHint.VISIBLE, false);
        setHint(WindowHint.RESIZABLE, true);
        setHint(WindowHint.CONTEXT_VERSION_MAJOR, 3);
        setHint(WindowHint.CONTEXT_VERSION_MINOR, 2);
        setHint(WindowHint.OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        setHint(WindowHint.OPENGL_FORWARD_COMPAT, true);
        //setHint(WindowHint.MAXIMIZED, true);

        // Create the window
        id = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (id == 0) {
            throw new RuntimeException("Failed to init the GLFW window");
        }

        GLFW.glfwSetFramebufferSizeCallback(id, (window, width, height) -> {
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

        this.mouse = new Mouse(id);
        this.keyboard = new Keyboard(id);
    }

    private void setHint(WindowHint hint, int value) {
        GLFW.glfwWindowHint(hint.get(), value);
    }

    private void setHint(WindowHint hint, boolean value) {
        GLFW.glfwWindowHint(hint.get(), value ? 1 : 0);
    }

    private void makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(id);
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
            setWindowPosition(w, h);
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
        GLFW.glfwShowWindow(id);
    }

    /**
     * Sets the window visible
     */
    private void hide() {
        GLFW.glfwHideWindow(id);
    }

    /**
     * Sets the position of the window
     *
     * @param x the x position of the window
     * @param y the y position of the window
     */
    public void setWindowPosition(int x, int y) {
        GLFW.glfwSetWindowPos(this.id, x, y);
    }

    /**
     * Sets the size of the window
     *
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void windowShouldClose(int width, int height) {
        GLFW.glfwSetWindowSize(this.id, width, height);
    }

    /**
     * Returns whether the window has been closed by the user
     *
     * @return true if window has been close else false
     */
    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(id);
    }

    /**
     * Returns whether the window has been closed by the user
     *
     * @return true if window has been close else false
     */
    public boolean isOpen() {
        return !GLFW.glfwWindowShouldClose(id);
    }

    /**
     * Sets the window should close flag. Used for closing up the program
     *
     * @param shouldClose true if wants the window to close else false
     */
    public void setWindowShouldClose(boolean shouldClose) {
        GLFW.glfwSetWindowShouldClose(id, shouldClose);
    }

    /**
     * Swap the buffers of the window
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(id);
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
     * Return the window's width
     *
     * @return the window's width
     */
    public int getX() {
        GLFW.glfwGetWindowPos(id, xPos, yPos);
        final int x = xPos.get();
        xPos.flip();
        return x;
    }

    /**
     * Return the window's height
     *
     * @return the window's height
     */
    public int getY() {
        GLFW.glfwGetWindowPos(id, xPos, yPos);
        final int y = yPos.get();
        yPos.flip();
        return y;
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
}

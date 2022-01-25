package org.saar.lwjgl.glfw.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.saar.lwjgl.glfw.event.EventListener;
import org.saar.lwjgl.glfw.event.EventListenersHelper;
import org.saar.lwjgl.glfw.event.IntValueChange;
import org.saar.lwjgl.glfw.input.keyboard.Keyboard;
import org.saar.lwjgl.glfw.input.mouse.Mouse;
import org.saar.lwjgl.opengl.fbos.ScreenFbo;
import org.saar.maths.objects.Dimensions;
import org.saar.maths.objects.RectangleI;

public class Window {

    private static Window current = null;

    static {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    private final long id;

    private final Mouse mouse;
    private final Keyboard keyboard;

    private final boolean vSync;
    private String title;

    private EventListenersHelper<ResizeEvent> resizeListenersHelper = EventListenersHelper.empty();
    private int width;
    private int height;

    private EventListenersHelper<PositionEvent> positionListenersHelper = EventListenersHelper.empty();
    private int x;
    private int y;

    public Window(long id, String title, int width, int height, boolean vSync) {
        this.id = id;
        this.vSync = vSync;
        this.title = title;
        this.width = width;
        this.height = height;
        this.mouse = new Mouse(this.id);
        this.keyboard = new Keyboard(this.id);
        init();
    }

    static Window create0(String title, int width, int height, boolean vSync) {
        final long id = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (id == 0) throw new RuntimeException("Failed to init the GLFW window");
        return new Window(id, title, width, height, vSync);
    }

    public static Window create(String title, int width, int height, boolean vSync) {
        final WindowBuilder builder = builder(title, width, height, vSync);
        builder.hint(WindowHints.visible(false))
                .hint(WindowHints.resizable());
        return builder.build();
    }

    public static WindowBuilder builder(String title, int width, int height, boolean vSync) {
        final WindowBuilder builder = new WindowBuilder(title, width, height, vSync);
        builder.hint(WindowHints.contextVersion(3, 2))
                .hint(WindowHints.openglProfile(OpenGlProfileType.CORE))
                .hint(WindowHints.openglForwardCompatibility());
        return builder;
    }

    public static Window current() {
        return Window.current;
    }

    public void addResizeListener(EventListener<ResizeEvent> listener) {
        this.resizeListenersHelper = this.resizeListenersHelper.addListener(listener);
    }

    public void addPositionListener(EventListener<PositionEvent> listener) {
        this.positionListenersHelper = this.positionListenersHelper.addListener(listener);
    }

    private void init() {
        GLFW.glfwSetFramebufferSizeCallback(this.id, (window, width, height) -> {
            final ResizeEvent event = new ResizeEvent(
                    new IntValueChange(this.width, width),
                    new IntValueChange(this.height, height)
            );
            this.width = width;
            this.height = height;
            this.resizeListenersHelper.fireEvent(event);

            ScreenFbo.getInstance().bind();
        });

        GLFW.glfwSetWindowPosCallback(this.id, (window, x, y) -> {
            final PositionEvent event = new PositionEvent(
                    new IntValueChange(this.x, x),
                    new IntValueChange(this.y, y)
            );
            this.x = x;
            this.y = y;
            this.positionListenersHelper.fireEvent(event);
        });

        center();
        makeContextCurrent();

        GLFW.glfwSwapInterval(this.vSync ? 1 : 0);

        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback(System.err);
        setVisible(true);
    }

    private void makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(this.id);
        Window.current = this;
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
    public void show() {
        GLFW.glfwShowWindow(this.id);
    }

    /**
     * Sets the window visible
     */
    public void hide() {
        GLFW.glfwHideWindow(this.id);
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
     * Returns the window's title
     *
     * @return the window's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the window visible
     */
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.id, title);
        this.title = title;
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
     * Sets the width of the window
     *
     * @param width the width of the window
     */
    public void setWidth(int width) {
        setSize(width, getHeight());
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
     * Sets the height of the window
     *
     * @param height the height of the window
     */
    public void setHeight(int height) {
        setSize(getWidth(), height);
    }

    /**
     * Sets the size of the window
     *
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(this.id, width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Return the window's x position
     *
     * @return the window's x position
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets the x position of the window
     *
     * @param x the x position of the window
     */
    public void setX(int x) {
        setPosition(x, getY());
    }

    /**
     * Return the window's y position
     *
     * @return the window's y position
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the y position of the window
     *
     * @param y the y position of the window
     */
    public void setY(int y) {
        setPosition(getX(), y);
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
     * Center the window in the middle of the screen
     */
    public void center() {
        final Dimensions dimensions = Monitor.primary.getDimensions();
        final int w = (dimensions.getWidth() - getWidth()) / 2;
        final int h = (dimensions.getHeight() - getHeight()) / 2;
        setPosition(w, h);
    }

    /**
     * Set the window to fullscreen
     */
    public void setFullscreen() {
        final Dimensions dimensions = Monitor.primary.getDimensions();
        GLFW.glfwSetWindowMonitor(this.id, Monitor.primary.id, 0, 0,
                dimensions.getWidth(), dimensions.getHeight(), GLFW.GLFW_DONT_CARE);

        this.width = dimensions.getWidth();
        this.height = dimensions.getHeight();
    }

    public void setMaximized() {
        final RectangleI workArea = Monitor.primary.getWorkArea();
        GLFW.glfwSetWindowMonitor(this.id, 0, workArea.x, workArea.y,
                workArea.w, workArea.h, GLFW.GLFW_DONT_CARE);

        GLFW.glfwMaximizeWindow(this.id);
        this.x = workArea.x;
        this.y = workArea.y;
        this.width = workArea.w;
        this.height = workArea.h;
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
     * Destroy the window and free all resources allocated in its context
     */
    public void destroy() {
        GLFW.glfwDestroyWindow(this.id);
    }
}

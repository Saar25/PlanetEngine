package org.saar.lwjgl.glfw.window

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GLUtil
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.glfw.event.EventListener
import org.saar.lwjgl.glfw.event.EventListenersHelper
import org.saar.lwjgl.glfw.event.IntValueChange
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.opengl.fbo.Fbo

class Window private constructor(
    private val id: Long,
    private var title: String,
    private var _width: Int,
    private var _height: Int,
    private val vSync: Boolean
) {

    /**
     * Creates the mouse that corresponds to this window
     * 
     * @return the mouse
     */
    val mouse = Mouse(this.id)

    /**
     * Returns the keyboard that corresponds to this window
     * 
     * @return the keyboard
     */
    val keyboard = Keyboard(this.id)

    private var resizeListenersHelper = EventListenersHelper.empty<ResizeEvent>()

    private var positionListenersHelper = EventListenersHelper.empty<PositionEvent>()
    private var _x = 0
    private var _y = 0

    var x: Int
        get() = _x
        set(value) {
            this._x = value
        }

    var y: Int
        get() = _y
        set(value) {
            this._y = value
        }

    var width: Int
        get() = _width
        set(value) {
            this._width = value
        }

    var height: Int
        get() = _height
        set(value) {
            this._height = value
        }

    init {
        init()
    }

    fun addResizeListener(listener: EventListener<ResizeEvent>) {
        this.resizeListenersHelper = this.resizeListenersHelper.addListener(listener)
    }

    fun addPositionListener(listener: EventListener<PositionEvent>) {
        this.positionListenersHelper = this.positionListenersHelper.addListener(listener)
    }

    private fun init() {
        GLFW.glfwSetFramebufferSizeCallback(this.id) { window: Long, width: Int, height: Int ->
            val event = ResizeEvent(
                IntValueChange(this.width, width),
                IntValueChange(this.height, height)
            )
            this.width = width
            this.height = height
            this.resizeListenersHelper.fireEvent(event)
            Fbo.NULL.bind()
        }

        GLFW.glfwSetWindowPosCallback(this.id) { window: Long, x: Int, y: Int ->
            val event = PositionEvent(
                IntValueChange(this.x, x),
                IntValueChange(this.y, y)
            )
            this.x = x
            this.y = y
            this.positionListenersHelper.fireEvent(event)
        }

        center()
        makeContextCurrent()

        GLFW.glfwSwapInterval(if (this.vSync) 1 else 0)

        GL.createCapabilities()
        GLUtil.setupDebugMessageCallback(System.out)

        MemoryStack.stackPush().use { stack ->
            val width = stack.mallocInt(1)
            val height = stack.mallocInt(1)
            GLFW.glfwGetWindowSize(this.id, width, height)
            this.width = width.get()
            this.height = height.get()
        }
        setVisible(true)
    }

    private fun makeContextCurrent() {
        GLFW.glfwMakeContextCurrent(this.id)
        current = this
    }

    /**
     * Sets the window visibility
     * 
     * @param visible true if the window should be visible, false otherwise
     */
    fun setVisible(visible: Boolean) {
        if (visible) show()
        else hide()
    }

    /**
     * Sets the window invisible
     */
    fun show() = GLFW.glfwShowWindow(this.id)

    /**
     * Sets the window visible
     */
    fun hide() = GLFW.glfwHideWindow(this.id)

    /**
     * Returns whether the window has been closed by the user
     * 
     * @return true if window has been close else false
     */
    fun windowShouldClose() = GLFW.glfwWindowShouldClose(this.id)

    val isOpen: Boolean
        /**
         * Returns whether the window has been closed by the user
         * 
         * @return true if window has been close else false
         */
        get() = !GLFW.glfwWindowShouldClose(this.id)

    /**
     * Sets the window should close flag. Used for closing up the program
     * 
     * @param shouldClose true if wants the window to close else false
     */
    fun setWindowShouldClose(shouldClose: Boolean) = GLFW.glfwSetWindowShouldClose(this.id, shouldClose)

    /**
     * Swap the buffers of the window
     */
    fun swapBuffers() = GLFW.glfwSwapBuffers(this.id)

    /**
     * Poll glfw events
     */
    fun pollEvents() = GLFW.glfwPollEvents()

    /**
     * wait for glfw events
     */
    fun waitEvents() = GLFW.glfwWaitEvents()

    /**
     * Returns the window's title
     * 
     * @return the window's title
     */
    fun getTitle() = this.title

    /**
     * Sets the window visible
     */
    fun setTitle(title: String) {
        GLFW.glfwSetWindowTitle(this.id, title)
        this.title = title
    }

    /**
     * Sets the size of the window
     * 
     * @param width  the width of the window
     * @param height the height of the window
     */
    fun setSize(width: Int, height: Int) {
        GLFW.glfwSetWindowSize(this.id, width, height)
        this.width = width
        this.height = height
    }

    /**
     * Sets the position of the window
     * 
     * @param x the x position of the window
     * @param y the y position of the window
     */
    fun setPosition(x: Int, y: Int) {
        if (GLFW.glfwGetPlatform() != GLFW.GLFW_PLATFORM_WAYLAND) {
            val vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
            if (vidMode != null) {
                GLFW.glfwSetWindowPos(this.id, x, y)
            }
        }
    }

    /**
     * Center the window in the middle of the screen
     */
    fun center() {
        val dimensions = Monitor.primary.dimensions
        val w = (dimensions.width - this.width) / 2
        val h = (dimensions.height - this.height) / 2
        setPosition(w, h)
    }

    /**
     * Set the window to fullscreen
     */
    fun setFullscreen() {
        val dimensions = Monitor.primary.dimensions
        GLFW.glfwSetWindowMonitor(
            this.id, Monitor.primary.id, 0, 0,
            dimensions.width, dimensions.height, GLFW.GLFW_DONT_CARE
        )

        this.width = dimensions.width
        this.height = dimensions.height
    }

    fun setMaximized() {
        val workArea = Monitor.primary.workArea
        GLFW.glfwSetWindowMonitor(
            this.id, 0, workArea.minX, workArea.minY,
            workArea.lengthX(), workArea.lengthY(), GLFW.GLFW_DONT_CARE
        )

        GLFW.glfwMaximizeWindow(this.id)
        this.x = workArea.minX
        this.y = workArea.minY
        this.width = workArea.lengthX()
        this.height = workArea.lengthY()
    }

    /**
     * Destroy the window and free all resources allocated in its context
     */
    fun destroy() = GLFW.glfwDestroyWindow(this.id)

    companion object {

        private var current: Window? = null

        init {
            // Set up an error callback. The default implementation
            // will print the error message in System.err.
            GLFWErrorCallback.createPrint(System.err).set()

            // Initialize GLFW. Most GLFW functions will not work before doing this.
            check(GLFW.glfwInit()) { "Unable to initialize GLFW" }
        }

        fun create0(title: String, width: Int, height: Int, vSync: Boolean): Window {
            val id = GLFW.glfwCreateWindow(width, height, title, 0, 0)
            if (id == 0L) throw RuntimeException("Failed to init the GLFW window")
            return Window(id, title, width, height, vSync)
        }

        @JvmStatic
        fun create(title: String, width: Int, height: Int, vSync: Boolean): Window {
            val builder: WindowBuilder = builder(title, width, height, vSync)
            builder.hint(WindowHints.visible(false))
                .hint(WindowHints.resizable())
            return builder.build()
        }

        @JvmStatic
        fun builder(title: String, width: Int, height: Int, vSync: Boolean): WindowBuilder {
            val builder = WindowBuilder(title, width, height, vSync)
            builder.hint(WindowHints.contextVersion(3, 2))
                .hint(WindowHints.openglProfile(OpenGlProfileType.CORE))
                .hint(WindowHints.openglForwardCompatibility())
            return builder
        }

        @JvmStatic
        fun current(): Window? {
            return current
        }
    }
}

package org.saar.lwjgl.opengl.utils;

import org.lwjgl.opengl.GL11;

public final class GlUtils {

    private GlUtils() {

    }

    public static int valueOf(boolean b) {
        return b ? GL11.GL_TRUE : GL11.GL_FALSE;
    }

    /**
     * Set the viewport of the screen, should be called whenever the effect of resizing the window is needed
     *
     * @param x x coordinate on the screen
     * @param y y coordinate on the screen
     * @param w width of the window
     * @param h height of the window
     */
    public static void setViewport(int x, int y, int w, int h) {
        GL11.glViewport(x, y, w, h);
    }

    /**
     * Clears the given buffer
     * <p>
     * Warning: the color mask, depth mask, and stencil mask has effect when clearing the buffers
     * when depth mask is disabled for example, the depth buffer will not clear!
     *
     * @param buffer1 the buffer to clear
     */
    public static void clear(GlBuffer buffer1) {
        GL11.glClear(buffer1.get());
    }

    /**
     * Clears the given buffers
     *
     * @see GlUtils#clear(GlBuffer)
     */
    public static void clear(GlBuffer buffer1, GlBuffer buffer2) {
        GL11.glClear(buffer1.get() | buffer2.get());
    }

    /**
     * Clears the given buffers
     *
     * @see GlUtils#clear(GlBuffer)
     */
    public static void clear(GlBuffer buffer1, GlBuffer buffer2, GlBuffer buffer3) {
        GL11.glClear(buffer1.get() | buffer2.get() | buffer3.get());
    }
}

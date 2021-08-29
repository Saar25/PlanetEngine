package org.saar.lwjgl.opengl.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

public final class GlUtils {

    private static boolean provokingVertexFirst = false;
    private static boolean polygonLines = false;

    private static GlCullFace cullFace = GlCullFace.NONE;

    private GlUtils() {

    }

    public static int valueOf(boolean b) {
        return b ? GL11.GL_TRUE : GL11.GL_FALSE;
    }

    public static boolean isPolygonLines() {
        return polygonLines;
    }

    /**
     * Sets the first vertex as the provoking vertex
     */
    public static void setProvokingVertexFirst() {
        if (!provokingVertexFirst) {
            GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION);
            provokingVertexFirst = true;
        }
    }

    /**
     * Sets the last vertex as the provoking vertex
     */
    public static void setProvokingVertexLast() {
        if (!provokingVertexFirst) {
            GL32.glProvokingVertex(GL32.GL_LAST_VERTEX_CONVENTION);
            provokingVertexFirst = false;
        }
    }

    /**
     * Setting the cull face
     *
     * @param face the face to cull, GlCullFace.NONE if disable
     */
    public static void setCullFace(GlCullFace face) {
        if (GlUtils.cullFace != face) {
            GlUtils.cullFace = face;
            setCullFace0(face);
        }
    }

    private static void setCullFace0(GlCullFace face) {
        if (face == GlCullFace.NONE) {
            GL11.glDisable(GL11.GL_CULL_FACE);
        } else {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(face.get());
        }
    }

    /**
     * Set the clear colour of the screen, the background of the window
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    public static void setClearColour(float r, float g, float b) {
        setClearColour(r, g, b, 1);
    }

    /**
     * Set the clear colour of the screen, the background of the window
     *
     * @param r red value
     * @param g green value
     * @param b blue value
     * @param a alpha value
     */
    public static void setClearColour(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
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
     * Render the meshes with lines, usually should be called for debugging
     */
    public static void drawPolygonLine() {
        if (!polygonLines) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            polygonLines = true;
        }
    }

    /**
     * Render the meshes as usual, usually should be called before rendering normally
     */
    public static void drawPolygonFill() {
        if (polygonLines) {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
            polygonLines = false;
        }
    }

    /**
     * Clears the colour buffer and the depth buffer, should be called before rendering the next frame
     */
    public static void clearColourAndDepthBuffer() {
        clear(GlBuffer.COLOUR, GlBuffer.DEPTH);
    }

    /**
     * Clears the buffers received
     *
     * @param glBuffers the buffers to clear
     */
    public static void clear(GlBuffer... glBuffers) {
        int mask = 0;
        for (GlBuffer glBuffer : glBuffers) {
            mask |= glBuffer.get();
        }
        GL11.glClear(mask);
    }

}

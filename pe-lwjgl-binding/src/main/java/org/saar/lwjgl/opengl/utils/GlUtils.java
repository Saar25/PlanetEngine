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

    public static void clear(GlBuffer buffer1) {
        GL11.glClear(buffer1.get());
    }

    public static void clear(GlBuffer buffer1, GlBuffer buffer2) {
        GL11.glClear(buffer1.get() | buffer2.get());
    }

    public static void clear(GlBuffer buffer1, GlBuffer buffer2, GlBuffer buffer3) {
        GL11.glClear(buffer1.get() | buffer2.get() | buffer3.get());
    }
}

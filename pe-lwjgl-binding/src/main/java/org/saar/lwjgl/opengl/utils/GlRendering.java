package org.saar.lwjgl.opengl.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.rhi.inputassembly.PrimitiveTopology;
import org.saar.rhi.opengl.inputassembly.OpenglInputAssemblyStateKt;

public final class GlRendering {

    private GlRendering() {

    }

    /**
     * Draws the bound vao using only his array buffers
     *
     * @param topology the topology
     * @param first    the first vertex
     * @param count    the number of vertices
     */
    public static void drawArrays(PrimitiveTopology topology, int first, int count) {
        final int mode = OpenglInputAssemblyStateKt.getGlValue(topology);
        GL11.glDrawArrays(mode, first, count);
    }

    /**
     * Draws the bound vao using his array buffers and element buffer (indices)
     *
     * @param topology the topology
     * @param count    the number of vertices
     * @param type     the type of the indices
     * @param indices  the number of the indices
     */
    public static void drawElements(PrimitiveTopology topology, int count, DataType type, long indices) {
        final int mode = OpenglInputAssemblyStateKt.getGlValue(topology);
        GL11.glDrawElements(mode, count, type.get(), indices);
    }

    /**
     * Draws the bound vao multiple times using only his array buffers
     *
     * @param topology  the topology
     * @param first     the first vertex
     * @param count     the number of vertices
     * @param instances the number of instances
     */
    public static void drawArraysInstanced(PrimitiveTopology topology, int first, int count, int instances) {
        final int mode = OpenglInputAssemblyStateKt.getGlValue(topology);
        GL31.glDrawArraysInstanced(mode, first, count, instances);
    }

    /**
     * Draws the bound vao multiple times using his array buffers and element buffer (indices)
     *
     * @param topology  the topology
     * @param count     the number of vertices
     * @param type      the type of the indices
     * @param indices   the number of the indices
     * @param instances the number of instances
     */
    public static void drawElementsInstanced(PrimitiveTopology topology, int count, DataType type, long indices, int instances) {
        final int mode = OpenglInputAssemblyStateKt.getGlValue(topology);
        GL31.glDrawElementsInstanced(mode, count, type.get(), indices, instances);
    }
}

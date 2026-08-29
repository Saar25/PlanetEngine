package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.rhi.inputassembly.PrimitiveTopology;

public class ArraysDrawCall implements DrawCall {

    private final PrimitiveTopology topology;
    private final int first;
    private final int count;

    public ArraysDrawCall(PrimitiveTopology topology, int first, int count) {
        this.topology = topology;
        this.first = first;
        this.count = count;
    }

    public ArraysDrawCall(PrimitiveTopology topology, int count) {
        this(topology, 0, count);
    }

    public PrimitiveTopology getTopology() {
        return this.topology;
    }

    public int getFirst() {
        return this.first;
    }

    public int getCount() {
        return this.count;
    }

    @Override
    public void doDrawCall() {
        GlRendering.drawArrays(this.topology, this.first, this.count);
    }
}

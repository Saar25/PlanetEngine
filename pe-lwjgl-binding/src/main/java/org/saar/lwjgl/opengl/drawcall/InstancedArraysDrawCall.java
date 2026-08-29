package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.rhi.inputassembly.PrimitiveTopology;

public class InstancedArraysDrawCall implements DrawCall {

    private final PrimitiveTopology topology;
    private final int first;
    private final int count;
    private int instances;

    public InstancedArraysDrawCall(PrimitiveTopology topology, int first, int count, int instances) {
        this.topology = topology;
        this.first = first;
        this.count = count;
        this.instances = instances;
    }

    public InstancedArraysDrawCall(PrimitiveTopology topology, int count, int instances) {
        this(topology, 0, count, instances);
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

    public int getInstances() {
        return this.instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    @Override
    public void doDrawCall() {
        GlRendering.drawArraysInstanced(this.topology, this.first, this.count, this.instances);
    }
}

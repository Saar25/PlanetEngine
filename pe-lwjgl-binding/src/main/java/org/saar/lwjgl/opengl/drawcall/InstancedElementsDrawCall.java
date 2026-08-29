package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.rhi.inputassembly.PrimitiveTopology;

public class InstancedElementsDrawCall implements DrawCall {

    private final PrimitiveTopology topology;
    private final int count;
    private final DataType type;
    private final long indices;
    private int instances;

    public InstancedElementsDrawCall(PrimitiveTopology topology, int count, DataType type, long indices, int instances) {
        this.topology = topology;
        this.count = count;
        this.type = type;
        this.indices = indices;
        this.instances = instances;
    }

    public InstancedElementsDrawCall(PrimitiveTopology topology, int count, DataType type, int instances) {
        this(topology, count, type, 0, instances);
    }

    public PrimitiveTopology getTopology() {
        return this.topology;
    }

    public int getCount() {
        return this.count;
    }

    public DataType getType() {
        return this.type;
    }

    public long getIndices() {
        return this.indices;
    }

    public int getInstances() {
        return this.instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    @Override
    public void doDrawCall() {
        GlRendering.drawElementsInstanced(this.topology, this.count, this.type, this.indices, this.instances);
    }
}

package org.saar.lwjgl.opengl.drawcall;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.utils.GlRendering;
import org.saar.rhi.inputassembly.PrimitiveTopology;

public class ElementsDrawCall implements DrawCall {

    private final PrimitiveTopology topology;
    private final int count;
    private final DataType type;
    private final long indices;

    public ElementsDrawCall(PrimitiveTopology topology, int count, DataType type, long indices) {
        this.topology = topology;
        this.count = count;
        this.type = type;
        this.indices = indices;
    }

    public ElementsDrawCall(PrimitiveTopology topology, int count, DataType type) {
        this(topology, count, type, 0);
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

    @Override
    public void doDrawCall() {
        GlRendering.drawElements(this.topology, this.count, this.type, this.indices);
    }
}

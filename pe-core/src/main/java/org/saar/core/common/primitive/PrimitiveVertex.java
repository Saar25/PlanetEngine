package org.saar.core.common.primitive;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public class PrimitiveVertex implements Vertex {

    private final GlPrimitive[] values;

    public PrimitiveVertex(GlPrimitive... values) {
        this.values = values;
    }

    public GlPrimitive[] getValues() {
        return this.values;
    }
}

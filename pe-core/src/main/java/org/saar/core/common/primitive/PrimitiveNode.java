package org.saar.core.common.primitive;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public class PrimitiveNode implements Node {

    private final GlPrimitive[] values;

    public PrimitiveNode(GlPrimitive... values) {
        this.values = values;
    }

    public GlPrimitive[] getValues() {
        return this.values;
    }

}

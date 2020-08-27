package org.saar.core.common.primitive;

import org.saar.core.node.AbstractNode;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public class PrimitiveNode extends AbstractNode implements Node {

    private final GlPrimitive[] values;

    public PrimitiveNode(GlPrimitive... values) {
        this.values = values;
    }

    public GlPrimitive[] getValues() {
        return this.values;
    }

}

package org.saar.example.primitive;

import org.saar.core.common.primitive.prototype.PrimitiveProperty;
import org.saar.core.common.primitive.prototype.PrimitivePrototype;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.primitive.GlFloat;

public class MyNode extends AbstractNode implements PrimitivePrototype, Node {

    @PrimitiveProperty
    private final GlFloat offset;

    public MyNode(GlFloat offset) {
        this.offset = offset;
    }
}

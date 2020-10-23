package org.saar.core.common.inference.weak;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.util.buffer.BufferWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeakInstance implements Node {

    private final List<GlPrimitive> primitives = new ArrayList<>();

    public WeakInstance with(GlPrimitive primitive) {
        this.primitives.add(primitive);
        return this;
    }

    public void write(BufferWriter writer) {
        for (GlPrimitive primitive : this.primitives) {
            primitive.write(writer);
        }
    }

    public Attribute[] getAttributes(int vertexAttributes) {
        int index = vertexAttributes;
        final List<Attribute> attributes = new ArrayList<>();
        for (GlPrimitive primitive : this.primitives) {
            Collections.addAll(attributes, primitive
                    .attribute(index++, false, 1));
        }
        return attributes.toArray(new Attribute[0]);
    }
}

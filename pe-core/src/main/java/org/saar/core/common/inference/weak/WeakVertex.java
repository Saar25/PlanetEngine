package org.saar.core.common.inference.weak;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeakVertex implements Vertex {

    private final List<GlPrimitive> primitives = new ArrayList<>();

    public WeakVertex with(GlPrimitive primitive) {
        this.primitives.add(primitive);
        return this;
    }

    public void write(BufferWriter writer) {
        for (GlPrimitive primitive : this.primitives) {
            primitive.write(writer);
        }
    }

    public Attribute[] getAttributes() {
        int index = 0;
        final List<Attribute> attributes = new ArrayList<>();
        for (GlPrimitive primitive : this.primitives) {
            Collections.addAll(attributes, primitive
                    .attribute(index++, false, 0));
        }
        return attributes.toArray(new Attribute[0]);
    }
}

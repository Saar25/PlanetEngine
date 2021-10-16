package org.saar.core.common.inference.weak;

import org.saar.core.mesh.Vertex;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.util.DataWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeakVertex implements Vertex {

    private final List<GlPrimitive> primitives = new ArrayList<>();

    public WeakVertex with(GlPrimitive primitive) {
        this.primitives.add(primitive);
        return this;
    }

    public void write(DataWriter writer) {
        for (GlPrimitive primitive : this.primitives) {
            primitive.write(writer);
        }
    }

    public IAttribute[] getAttributes() {
        int index = 0;
        final List<IAttribute> attributes = new ArrayList<>();
        for (GlPrimitive primitive : this.primitives) {
            Collections.addAll(attributes, primitive
                    .attribute(index++, false, 0));
        }
        return attributes.toArray(new IAttribute[0]);
    }
}

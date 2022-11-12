package org.saar.core.common.inference.weak;

import org.saar.core.mesh.Instance;
import org.saar.lwjgl.opengl.attribute.AttributeComposite;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.util.DataWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeakInstance implements Instance {

    private final List<GlPrimitive> primitives = new ArrayList<>();

    public WeakInstance with(GlPrimitive primitive) {
        this.primitives.add(primitive);
        return this;
    }

    public void write(DataWriter writer) {
        for (GlPrimitive primitive : this.primitives) {
            primitive.write(writer);
        }
    }

    public IAttribute getAttribute(int vertexAttributes) {
        int index = vertexAttributes;
        final List<IAttribute> attributes = new ArrayList<>();
        for (GlPrimitive primitive : this.primitives) {
            Collections.addAll(attributes, primitive
                    .attribute(index++, false, 1));
        }
        return new AttributeComposite(attributes);
    }
}

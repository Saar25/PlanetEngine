package org.saar.core.common.primitive;

import org.saar.core.common.primitive.prototype.PrimitiveLocator;
import org.saar.core.common.primitive.prototype.PrimitivePrototype;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

import java.util.List;

public final class Primitives {

    private Primitives() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static PrimitiveVertex vertexFromPrototype(PrimitivePrototype prototype) {
        final PrimitiveLocator locator = new PrimitiveLocator(prototype);
        final List<GlPrimitive> properties = locator.getPrimitiveProperties();
        return new PrimitiveVertex(properties.toArray(new GlPrimitive[0]));
    }

    public static PrimitiveNode nodeFromPrototype(PrimitivePrototype prototype) {
        final PrimitiveLocator locator = new PrimitiveLocator(prototype);
        final List<GlPrimitive> properties = locator.getPrimitiveProperties();
        return new PrimitiveNode(properties.toArray(new GlPrimitive[0]));
    }
}

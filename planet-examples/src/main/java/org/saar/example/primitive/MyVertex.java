package org.saar.example.primitive;

import org.saar.core.common.primitive.prototype.PrimitiveProperty;
import org.saar.core.common.primitive.prototype.PrimitivePrototype;
import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;

public class MyVertex implements PrimitivePrototype, Vertex {

    @PrimitiveProperty
    private final GlFloat2 position;

    @PrimitiveProperty
    private final GlFloat3 colour;

    public MyVertex(GlFloat2 position, GlFloat3 colour) {
        this.position = position;
        this.colour = colour;
    }
}

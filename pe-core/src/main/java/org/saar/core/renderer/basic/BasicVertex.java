package org.saar.core.renderer.basic;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.vertex.ModelBuffer;
import org.saar.core.model.vertex.ModelVertex;

public class BasicVertex implements ModelVertex {

    private final Vector2fc position;
    private final Vector3fc colour;

    public BasicVertex(Vector2fc position, Vector3fc colour) {
        this.position = position;
        this.colour = colour;
    }

    @Override
    public void write(ModelBuffer buffer) {
        buffer.write(this.position.x());
        buffer.write(this.position.y());
        buffer.write(this.colour.x());
        buffer.write(this.colour.y());
        buffer.write(this.colour.z());
    }
}

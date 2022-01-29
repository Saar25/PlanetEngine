package org.saar.minecraft.entity;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector3ic;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.ReadonlyPosition;
import org.saar.maths.utils.Vector3;
import org.saar.minecraft.World;

import java.util.ArrayList;
import java.util.List;

public class HitBox {

    private final Vector3fc[] vertices;

    public HitBox(Vector3fc... vertices) {
        this.vertices = vertices;
    }

    public static HitBox build(float radius, float height) {
        final List<Vector3fc> vertices = new ArrayList<>((int) height * 4 + 4);
        for (int i = 0; i < height; i++) {
            addVertices(vertices, radius, i);
        }
        if (height != (int) height) {
            addVertices(vertices, radius, height);
        }
        return new HitBox(vertices.toArray(new Vector3fc[0]));
    }

    private static void addVertices(List<Vector3fc> vertices, float radius, float height) {
        vertices.add(Vector3.of(+radius, radius - height, +radius));
        vertices.add(Vector3.of(+radius, radius - height, -radius));
        vertices.add(Vector3.of(-radius, radius - height, -radius));
        vertices.add(Vector3.of(-radius, radius - height, +radius));
    }

    public Vector3f collideWithWorld(World world, ReadonlyPosition cameraPosition, Vector3fc direction) {
        final Vector3f ensured = Vector3.of(direction);
        for (Vector3fc vertex : this.vertices) {
            final Position vertexPosition = Position.create();
            vertexPosition.set(cameraPosition);
            vertexPosition.add(vertex);

            Collision.ensureDirection(world, vertexPosition, ensured);
        }
        return ensured;
    }

    public boolean isCollidingBlock(Position position, Vector3ic block) {
        for (Vector3fc vertex : this.vertices) {
            final Position vertexPosition = Position.create();
            vertexPosition.set(position);
            vertexPosition.add(vertex);

            if (Collision.isCollidingBlock(vertexPosition, block)) {
                return true;
            }
        }

        return false;
    }
}

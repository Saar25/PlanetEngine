package org.saar.core.common.r3d;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;

public class Mesh3D implements Mesh {

    private final Mesh mesh;

    private Mesh3D(Mesh mesh) {
        this.mesh = mesh;
    }

    static Mesh3D create(Mesh3DPrototype prototype, int indices, int instances) {
        return new Mesh3D(Meshes.toInstancedElementsMesh(prototype, indices, instances));
    }

    public static Mesh3D load(Mesh3DPrototype prototype, Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        return Mesh3DBuilder.build(prototype, vertices, indices, instances).load();
    }

    public static Mesh3D load(Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        return Mesh3DBuilder.build(R3D.mesh(), vertices, indices, instances).load();
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}

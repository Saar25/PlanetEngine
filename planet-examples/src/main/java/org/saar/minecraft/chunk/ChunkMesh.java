package org.saar.minecraft.chunk;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;

public class ChunkMesh implements Mesh {

    private final Mesh mesh;

    public ChunkMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    static ChunkMesh create(ChunkMeshPrototype prototype, int vertices) {
        return new ChunkMesh(Meshes.toArraysMesh(prototype, vertices));
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

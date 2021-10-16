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

    public static ChunkMesh load(ChunkMeshPrototype prototype, ChunkVertex[] vertices) {
        return ChunkMeshBuilder.build(prototype, vertices).load();
    }

    public static ChunkMesh load(ChunkVertex[] vertices) {
        return ChunkMeshBuilder.build(Chunks.meshPrototype(), vertices).load();
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

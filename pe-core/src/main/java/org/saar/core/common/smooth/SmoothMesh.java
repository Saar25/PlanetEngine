package org.saar.core.common.smooth;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;
import org.saar.core.mesh.async.FutureMesh;

import java.util.concurrent.CompletableFuture;

public class SmoothMesh implements Mesh {

    private final Mesh mesh;

    private SmoothMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    static SmoothMesh create(SmoothMeshPrototype prototype, int indices) {
        return new SmoothMesh(Meshes.toElementsDrawCallMesh(prototype, indices));
    }

    public static SmoothMesh load(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        return SmoothMeshBuilder.build(prototype, vertices, indices).load();
    }

    public static SmoothMesh load(SmoothVertex[] vertices, int[] indices) {
        return SmoothMeshBuilder.build(Smooth.mesh(), vertices, indices).load();
    }

    public static SmoothMesh loadAsync(CompletableFuture<SmoothMeshBuilder> builder) {
        return new SmoothMesh(FutureMesh.unloaded(builder));
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

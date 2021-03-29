package org.saar.minecraft.chunk;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class ChunkMesh implements Mesh {

    private final Mesh mesh;

    private ChunkMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    static ChunkMesh create(ChunkMeshPrototype prototype, int vertices) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.store();

        final DrawCall drawCall = new ArraysDrawCall(
                RenderMode.TRIANGLES, vertices);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new ChunkMesh(mesh);
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

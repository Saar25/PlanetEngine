package org.saar.core.common.smooth;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.async.FutureMesh;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

import java.util.concurrent.CompletableFuture;

public class SmoothMesh implements Mesh {

    private final Mesh mesh;

    private SmoothMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    static SmoothMesh create(SmoothMeshPrototype prototype, int indices) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new SmoothMesh(mesh);
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

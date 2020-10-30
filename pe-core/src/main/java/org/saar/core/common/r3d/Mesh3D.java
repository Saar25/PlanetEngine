package org.saar.core.common.r3d;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class Mesh3D implements Mesh {

    private final Mesh mesh;

    private Mesh3D(Mesh mesh) {
        this.mesh = mesh;
    }

    static Mesh3D create(Mesh3DPrototype prototype, int indices, int instances) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.store();

        final DrawCall drawCall = new InstancedElementsDrawCall(
                RenderMode.TRIANGLES, indices, DataType.U_INT, instances);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new Mesh3D(mesh);
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

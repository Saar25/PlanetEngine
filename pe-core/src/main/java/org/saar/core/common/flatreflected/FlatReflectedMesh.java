package org.saar.core.common.flatreflected;

import org.saar.core.model.DrawCall;
import org.saar.core.model.DrawCallMesh;
import org.saar.core.model.ElementsDrawCall;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class FlatReflectedMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);
    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, false);

    private final Mesh mesh;

    public FlatReflectedMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void setUpPrototype(FlatReflectedMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
    }

    public static FlatReflectedMesh load(FlatReflectedMeshPrototype prototype, FlatReflectedVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final FlatReflectedMeshWriter writer = new FlatReflectedMeshWriter(prototype);
        MeshWriters.writeVertices(writer, vertices);
        MeshWriters.writeIndices(writer, indices);

        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new FlatReflectedMesh(mesh);
    }

    public static FlatReflectedMesh load(FlatReflectedVertex[] vertices, int[] indices) {
        final FlatReflectedMeshPrototype prototype = FlatReflected.meshPrototype();
        return FlatReflectedMesh.load(prototype, vertices, indices);
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

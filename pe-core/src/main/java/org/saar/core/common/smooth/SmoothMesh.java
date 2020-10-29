package org.saar.core.common.smooth;

import org.saar.core.model.DrawCallMesh;
import org.saar.core.model.FutureMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

import java.util.concurrent.CompletableFuture;

public class SmoothMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute targetAttribute = Attribute.of(3, 3, DataType.FLOAT, true);

    private final Mesh mesh;

    private SmoothMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void addAttributes(SmoothMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTargetBuffer().addAttributes(targetAttribute);
    }

    static void initPrototype(SmoothMeshPrototype prototype, int vertices, int indices) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
    }

    static SmoothMesh create(SmoothMeshPrototype prototype, int indices) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new SmoothMesh(mesh);
    }

    public static SmoothMesh load(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        final SmoothMeshBuilder builder = SmoothMeshBuilder.create(
                prototype, vertices.length, indices.length);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder.load();
    }

    public static SmoothMesh load(SmoothVertex[] vertices, int[] indices) {
        return SmoothMesh.load(Smooth.mesh(), vertices, indices);
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

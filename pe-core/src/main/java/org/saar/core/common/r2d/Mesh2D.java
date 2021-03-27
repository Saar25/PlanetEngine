package org.saar.core.common.r2d;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class Mesh2D implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 2, DataType.FLOAT, false);

    private static final Attribute colourAttribute = Attribute.of(1, 3, DataType.FLOAT, false);

    private final Mesh mesh;

    private Mesh2D(Mesh mesh) {
        this.mesh = mesh;
    }

    public static Mesh2D load(Mesh2DPrototype prototype, Vertex2D[] vertices, int[] indices) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final Mesh2DWriter writer = new Mesh2DWriter(prototype);
        writer.writeVertices(vertices);
        writer.writeIndices(indices);

        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new Mesh2D(mesh);
    }

    public static Mesh2D load(Vertex2D[] vertices, int[] indices) {
        return Mesh2D.load(R2D.mesh(), vertices, indices);
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

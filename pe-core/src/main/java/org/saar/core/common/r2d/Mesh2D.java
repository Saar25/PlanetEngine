package org.saar.core.common.r2d;

import org.saar.core.model.ElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.ModelWriters;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
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
        ModelWriters.writeVertices(writer, vertices);
        ModelWriters.writeIndices(writer, indices);

        helper.store();

        final Mesh mesh = new ElementsMesh(vao,
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        return new Mesh2D(mesh);
    }

    public static Mesh2D load(Vertex2D[] vertices, int[] indices) {
        final Mesh2DPrototype prototype = new Mesh2DPrototypeImpl();
        return Mesh2D.load(prototype, vertices, indices);
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

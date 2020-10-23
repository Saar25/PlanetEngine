package org.saar.core.common.smooth;

import org.saar.core.model.DrawCallMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class SmoothMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute targetAttribute = Attribute.of(3, 3, DataType.FLOAT, true);

    private final Mesh mesh;

    private SmoothMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void setUpPrototype(SmoothMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTargetBuffer().addAttributes(targetAttribute);
    }

    public static SmoothMesh load(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final SmoothMeshWriter writer = new SmoothMeshWriter(prototype);
        MeshWriters.writeVertices(writer, vertices);
        MeshWriters.writeIndices(writer, indices);

        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new SmoothMesh(mesh);
    }

    public static SmoothMesh load(SmoothVertex[] vertices, int[] indices) {
        return SmoothMesh.load(Smooth.mesh(), vertices, indices);
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

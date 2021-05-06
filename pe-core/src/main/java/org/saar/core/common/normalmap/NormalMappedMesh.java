package org.saar.core.common.normalmap;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class NormalMappedMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);
    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);
    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);
    private static final Attribute tangentAttribute = Attribute.of(3, 3, DataType.FLOAT, false);
    private static final Attribute biTangentAttribute = Attribute.of(4, 3, DataType.FLOAT, false);

    private final Mesh mesh;

    public NormalMappedMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void setUpPrototype(NormalMappedMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getUvCoordBuffer().addAttribute(uvCoordAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getTangentBuffer().addAttribute(tangentAttribute);
        prototype.getBiTangentBuffer().addAttribute(biTangentAttribute);
    }

    static NormalMappedMesh create(NormalMappedMeshPrototype prototype, int indices) {
        return new NormalMappedMesh(Meshes.toElementsDrawCallMesh(prototype, indices));
    }

    public static NormalMappedMesh load(NormalMappedMeshPrototype prototype, NormalMappedVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final NormalMappedMeshWriter writer = new NormalMappedMeshWriter(prototype);
        writer.writeVertices(vertices);
        writer.writeIndices(indices);

        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new NormalMappedMesh(mesh);
    }

    public static NormalMappedMesh load(NormalMappedVertex[] vertices, int[] indices) {
        return NormalMappedMesh.load(NormalMapped.mesh(), vertices, indices);
    }

    public static NormalMappedMesh load(String objFile) throws Exception {
        final NormalMappedMeshPrototype prototype = NormalMapped.mesh();
        setUpPrototype(prototype);

        try (final NormalMappedMeshLoader loader = new NormalMappedMeshLoader(objFile)) {
            final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
            helper.allocateVertices(loader.vertexCount());
            helper.allocateIndices(loader.indexCount());

            final NormalMappedMeshWriter writer = new NormalMappedMeshWriter(prototype);
            writer.writeVertices(loader.loadVertices());
            writer.writeIndices(loader.loadIndices());

            return NormalMappedMesh.create(prototype, loader.indexCount());
        }
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

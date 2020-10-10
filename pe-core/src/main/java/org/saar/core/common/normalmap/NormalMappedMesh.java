package org.saar.core.common.normalmap;

import org.saar.core.model.ElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.*;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
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

    public static NormalMappedMesh load(NormalMappedMeshPrototype prototype, NormalMappedVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final NormalMappedMeshWriter writer = new NormalMappedMeshWriter(prototype);
        MeshWriters.writeVertices(writer, vertices);
        MeshWriters.writeIndices(writer, indices);

        helper.store();

        final Mesh mesh = new ElementsMesh(vao,
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        return new NormalMappedMesh(mesh);
    }

    public static NormalMappedMesh load(NormalMappedVertex[] vertices, int[] indices) {
        final NormalMappedMeshPrototype prototype = new NormalMappedMeshPrototypeImpl();
        return NormalMappedMesh.load(prototype, vertices, indices);
    }

    public static NormalMappedMesh load(String objFile) throws Exception {
        final NormalMappedMeshPrototypeImpl prototype = new NormalMappedMeshPrototypeImpl();
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();

        try (final AssimpMesh assimpMesh = AssimpUtil.load(objFile)) {
            assimpMesh.writeDataBuffer(prototype.getMeshVertexBuffer().getWrapper(),
                    new AssimpPositionComponent(),
                    new AssimpTexCoordComponent(0),
                    new AssimpNormalComponent(),
                    new AssimpTangentComponent(),
                    new AssimpBiTangentComponent());

            assimpMesh.writeIndexBuffer(prototype.getMeshIndexBuffer().getWrapper());

            helper.store();
            helper.loadToVao(vao);

            final Mesh mesh = new ElementsMesh(vao, RenderMode.TRIANGLES,
                    assimpMesh.indexCount(), DataType.U_INT);
            return new NormalMappedMesh(mesh);
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

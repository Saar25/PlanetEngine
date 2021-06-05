package org.saar.core.common.normalmap;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;

import java.io.IOException;
import java.util.Collection;

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

    private static NormalMappedMesh create(NormalMappedMeshPrototype prototype, int indices) {
        return new NormalMappedMesh(Meshes.toElementsMesh(prototype, indices));
    }

    public static NormalMappedMesh load(NormalMappedMeshPrototype prototype, NormalMappedVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices.length);
        helper.allocateIndices(indices.length);

        final NormalMappedMeshWriter writer = new NormalMappedMeshWriter(prototype);
        writer.writeVertices(vertices);
        writer.writeIndices(indices);

        return NormalMappedMesh.create(prototype, indices.length);
    }

    public static NormalMappedMesh load(NormalMappedVertex[] vertices, int[] indices) {
        return NormalMappedMesh.load(NormalMapped.mesh(), vertices, indices);
    }

    public static NormalMappedMesh load(NormalMappedMeshPrototype prototype,
                                        Collection<NormalMappedVertex> vertices,
                                        Collection<Integer> indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices.size());
        helper.allocateIndices(indices.size());

        final NormalMappedMeshWriter writer = new NormalMappedMeshWriter(prototype);
        writer.writeVertices(vertices);
        writer.writeIndices(indices);

        return NormalMappedMesh.create(prototype, indices.size());
    }

    public static NormalMappedMesh load(Collection<NormalMappedVertex> vertices, Collection<Integer> indices) {
        return load(NormalMapped.mesh(), vertices, indices);
    }

    public static NormalMappedMesh load(NormalMappedMeshPrototype prototype, String objFile) throws IOException {
        try (final NormalMappedMeshLoader loader = new NormalMappedMeshLoader(objFile)) {
            return load(prototype, loader.loadVertices(), loader.loadIndices());
        }
    }

    public static NormalMappedMesh load(String objFile) throws Exception {
        return NormalMappedMesh.load(NormalMapped.mesh(), objFile);
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

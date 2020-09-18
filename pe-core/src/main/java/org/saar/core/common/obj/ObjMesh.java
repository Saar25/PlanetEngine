package org.saar.core.common.obj;


import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.model.Mesh;
import org.saar.lwjgl.assimp.AssimpData;
import org.saar.lwjgl.assimp.AssimpUtil;

public class ObjMesh implements Mesh {

    private final ObjModelBuffers buffers;

    public ObjMesh(ObjVertex[] vertices, int[] indices) {
        this.buffers = ObjModelBuffers.singleDataBuffer(vertices.length, indices.length);
        this.buffers.load(vertices, indices);
    }

    public static ObjMesh load(String objFile) throws Exception {
        final AssimpData mesh = AssimpUtil.load(objFile);
        return new ObjMesh(toVertices(mesh), toIndices(mesh));
    }

    private static int[] toIndices(AssimpData mesh) {
        final int[] indices = new int[mesh.getIndices().length];
        for (int i = 0; i < mesh.getIndices().length; i++) {
            indices[i] = mesh.getIndices()[i].getValue();
        }
        return indices;
    }

    private static ObjVertex[] toVertices(AssimpData mesh) {
        final int vertexCount = mesh.getPositions().length;
        final ObjVertex[] vertices = new ObjVertex[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            final Vector3fc position = mesh.getPositions()[i].getValue();
            final Vector2fc uvCoord = mesh.getUvCoords()[i].getValue();
            final Vector3fc normal = mesh.getNormals()[i].getValue();
            vertices[i] = ObjVertex.of(position, uvCoord, normal);
        }
        return vertices;
    }

    @Override
    public void draw() {
        this.buffers.getMesh().draw();
    }

    @Override
    public void delete() {
        this.buffers.getMesh().delete();
    }
}

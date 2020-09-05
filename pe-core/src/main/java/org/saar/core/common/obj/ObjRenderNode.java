package org.saar.core.common.obj;

import org.joml.Vector2fc;
import org.joml.Vector3fc;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.lwjgl.assimp.AssimpData;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.objects.Transform;

public class ObjRenderNode extends AbstractNode implements RenderNode, ObjNode {

    private final ObjModelBuffers mesh;
    private final ObjNode instance;

    public ObjRenderNode(ObjVertexPrototype[] vertices, int[] indices, ObjNode instance) {
        this.mesh = new ObjModelBuffersOneVbo(vertices.length, indices.length);
        this.mesh.load(vertices, indices);
        this.instance = instance;
    }

    public static ObjRenderNode load(String objFile, ObjNode node) throws Exception {
        final AssimpData mesh = AssimpUtil.load(objFile);
        return new ObjRenderNode(toVertices(mesh), toIndices(mesh), node);
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
            vertices[i] = new ObjVertex(position, uvCoord, normal);
        }
        return vertices;
    }

    @Override
    public Transform getTransform() {
        return this.instance.getTransform();
    }

    @Override
    public ReadOnlyTexture getTexture() {
        return this.instance.getTexture();
    }

    @Override
    public void render() {
        this.mesh.getModel().draw();
    }

    @Override
    public void delete() {
        this.mesh.getModel().delete();
    }
}

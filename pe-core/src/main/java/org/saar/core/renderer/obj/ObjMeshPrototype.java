package org.saar.core.renderer.obj;

public class ObjMeshPrototype {

    private final int[] indices;
    private final ObjVertexPrototype[] vertices;

    public ObjMeshPrototype(int[] indices, ObjVertexPrototype[] vertices) {
        this.indices = indices;
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }

    public ObjVertexPrototype[] getVertices() {
        return this.vertices;
    }
}

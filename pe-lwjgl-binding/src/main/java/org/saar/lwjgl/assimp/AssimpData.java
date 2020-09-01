package org.saar.lwjgl.assimp;

import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.primitive.GlUInt;

public class AssimpData {

    private GlFloat3[] positions;
    private GlFloat2[] uvCoords;
    private GlFloat3[] normals;
    private GlUInt[] indices;

    public void loadPositions(GlFloat3... positions) {
        this.positions = positions;
    }

    public void loadUvCoords(GlFloat2... uvCoords) {
        this.uvCoords = uvCoords;
    }

    public void loadNormals(GlFloat3... normals) {
        this.normals = normals;
    }

    public void loadIndices(GlUInt... indices) {
        this.indices = indices;
    }

    public GlFloat3[] getPositions() {
        return this.positions;
    }

    public GlFloat2[] getUvCoords() {
        return this.uvCoords;
    }

    public GlFloat3[] getNormals() {
        return this.normals;
    }

    public GlUInt[] getIndices() {
        return this.indices;
    }
}

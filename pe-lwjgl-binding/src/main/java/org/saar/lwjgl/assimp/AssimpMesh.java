package org.saar.lwjgl.assimp;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

public class AssimpMesh implements AutoCloseable {

    private final AIScene aiScene;
    private final AIMesh aiMesh;

    public AssimpMesh(AIScene aiScene, AIMesh aiMesh) {
        this.aiScene = aiScene;
        this.aiMesh = aiMesh;
    }

    public AIMesh getAiMesh() {
        return this.aiMesh;
    }

    public int vertexCount() {
        return getAiMesh().mVertices().limit();
    }

    public int indexCount() {
        return getAiMesh().mFaces().limit() * 3;
    }

    @Override
    public void close() {
        Assimp.aiReleaseImport(this.aiScene);
    }
}

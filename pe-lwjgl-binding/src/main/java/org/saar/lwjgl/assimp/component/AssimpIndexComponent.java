package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;

import java.nio.IntBuffer;

public class AssimpIndexComponent implements AssimpComponent {

    private final AIFace.Buffer buffer;

    private AIFace currentFace;
    private IntBuffer currentBuffer;

    public AssimpIndexComponent(AIFace.Buffer buffer) {
        this.buffer = buffer;

        this.currentFace = this.buffer.get();
        this.currentBuffer = this.currentFace.mIndices();
    }

    public static AssimpIndexComponent of(AIMesh aiMesh) {
        final AIFace.Buffer buffer = aiMesh.mFaces();
        AssimpUtil.requiredNotNull(buffer, "Index data not found");

        return new AssimpIndexComponent(buffer);
    }

    public int next() {
        if (!this.currentBuffer.hasRemaining()) {
            this.currentFace = this.buffer.get();
            this.currentBuffer = this.currentFace.mIndices();
        }
        return this.currentBuffer.get();
    }

    @Override
    public int count() {
        return this.buffer.limit() * 3;
    }
}

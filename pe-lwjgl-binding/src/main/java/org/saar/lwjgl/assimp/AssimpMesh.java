package org.saar.lwjgl.assimp;

import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.objects.vbos.VboWrapper;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.nio.ByteBuffer;

public class AssimpMesh implements AutoCloseable {

    private final AIScene aiScene;
    private final AIMesh aiMesh;

    public AssimpMesh(AIScene aiScene, AIMesh aiMesh) {
        this.aiScene = aiScene;
        this.aiMesh = aiMesh;
    }

    public void writeDataBuffer(VboWrapper vbo, AssimpComponent... components) throws AssimpException {
        final int vertices = this.aiMesh.mVertices().limit();
        final int bytes = AssimpComponent.bytes(components);

        vbo.allocate(bytes * vertices);

        for (AssimpComponent component : components) {
            component.init(this.aiMesh);
        }
        for (int i = 0; i < vertices; i++) {
            for (AssimpComponent component : components) {
                component.next(vbo.getWriter());
            }
        }
    }

    public void writeIndexBuffer(VboWrapper vbo) throws AssimpException {
        final AIFace.Buffer faceBuffer = this.aiMesh.mFaces();

        vbo.allocate(faceBuffer.limit() * 4 * 3);

        AssimpUtil.requiredNotNull(faceBuffer, "Indices data not found");
        AssimpUtil.writeIndexBuffer(vbo.getWriter(), faceBuffer);
    }

    public int indexCount() {
        return this.aiMesh.mFaces().limit() * 4 * 3;
    }

    public ByteBuffer allocateByteBuffer(AssimpComponent... components) throws AssimpException {
        final int vertices = this.aiMesh.mVertices().limit();
        final int bytes = AssimpComponent.bytes(components);

        final ByteBuffer byteBuffer = MemoryUtil.memAlloc(bytes * vertices);
        final BufferWriter bufferWriter = new BufferWriter(byteBuffer);

        for (AssimpComponent component : components) {
            component.init(this.aiMesh);
        }
        for (int i = 0; i < vertices; i++) {
            for (AssimpComponent component : components) {
                component.next(bufferWriter);
            }
        }

        byteBuffer.flip();
        return byteBuffer;
    }

    public ByteBuffer allocateIndexBuffer() throws AssimpException {
        final AIFace.Buffer faceBuffer = this.aiMesh.mFaces();
        AssimpUtil.requiredNotNull(faceBuffer, "Indices data not found");
        return AssimpUtil.toIndexBuffer(faceBuffer);
    }

    @Override
    public void close() {
        Assimp.aiReleaseImport(this.aiScene);
    }
}

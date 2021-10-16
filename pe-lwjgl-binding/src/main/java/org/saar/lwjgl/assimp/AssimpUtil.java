package org.saar.lwjgl.assimp;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.util.FileLoader;

import java.nio.ByteBuffer;

public class AssimpUtil {

    public static void requiredNotNull(Object object, String message) throws AssimpException {
        if (object == null) throw new AssimpException(message);
    }

    public static AssimpMesh load(String resourcePath) throws Exception {
        return AssimpUtil.load(resourcePath, Assimp.aiProcess_JoinIdenticalVertices |
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals |
                Assimp.aiProcess_CalcTangentSpace);
    }

    public static AssimpMesh load(String resourcePath, int flags) throws Exception {
        final AIScene aiScene = AssimpUtil.loadScene(resourcePath, flags);
        if (aiScene == null) {
            throw new AssimpException(Assimp.aiGetErrorString());
        }

        final PointerBuffer aiMeshes = aiScene.mMeshes();
        if (aiMeshes == null) {
            throw new AssimpException(Assimp.aiGetErrorString());
        }

        final AIMesh aiMesh = AIMesh.create(aiMeshes.get(0));

        return new AssimpMesh(aiScene, aiMesh);
    }

    private static AIScene loadScene(String path, int flags) {
        final byte[] source = FileLoader.loadTextFile(path).getBytes();
        final ByteBuffer byteBuffer = MemoryUtil.memAlloc(source.length).put(source);
        byteBuffer.flip();
        final AIScene aiScene = Assimp.aiImportFileFromMemory(byteBuffer, flags, "");
        MemoryUtil.memFree(byteBuffer);
        return aiScene;
    }
}

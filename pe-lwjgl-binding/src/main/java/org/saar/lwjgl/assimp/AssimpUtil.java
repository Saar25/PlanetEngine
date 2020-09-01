package org.saar.lwjgl.assimp;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.primitive.GlFloat2;
import org.saar.lwjgl.opengl.primitive.GlFloat3;
import org.saar.lwjgl.opengl.primitive.GlUInt;
import org.saar.lwjgl.opengl.utils.MemoryUtils;
import org.saar.utils.file.TextFileLoader;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class AssimpUtil {

    public static AssimpData load(String resourcePath) throws Exception {
        return AssimpUtil.load(resourcePath, Assimp.aiProcess_JoinIdenticalVertices |
                Assimp.aiProcess_Triangulate | Assimp.aiProcess_FixInfacingNormals);
    }

    public static AssimpData load(String resourcePath, int flags) throws Exception {
        final AIScene aiScene = AssimpUtil.loadScene(resourcePath, flags);
        if (aiScene == null) {
            throw new Exception(Assimp.aiGetErrorString());
        }

        final PointerBuffer aiMeshes = aiScene.mMeshes();
        final long address = aiMeshes.get(0);
        final AIMesh aiMesh = AIMesh.create(address);
        final AssimpData mesh = AssimpUtil.processMesh(aiMesh);

        Assimp.aiReleaseImport(aiScene);

        return mesh;
    }

    private static AIScene loadScene(String path, int flags) throws Exception {
        final String source = TextFileLoader.loadResource(path);
        final ByteBuffer byteBuffer = MemoryUtils.loadToByteBuffer(source.getBytes());
        final AIScene aiScene = Assimp.aiImportFileFromMemory(byteBuffer, flags, "");
        MemoryUtil.memFree(byteBuffer);
        return aiScene;
    }

    private static AssimpData processMesh(AIMesh aiMesh) {
        final AssimpData mesh = new AssimpData();

        final AIVector3D.Buffer verticesBuffer = aiMesh.mVertices();
        final List<GlFloat3> vertices = processPositions(verticesBuffer);
        mesh.loadPositions(vertices.toArray(new GlFloat3[0]));

        final AIVector3D.Buffer uvCoordsBuffer = aiMesh.mTextureCoords(0);
        if (uvCoordsBuffer != null) {
            final List<GlFloat2> uvCoords = processUvCoords(uvCoordsBuffer);
            mesh.loadUvCoords(uvCoords.toArray(new GlFloat2[0]));
        }

        final AIVector3D.Buffer normalsBuffer = aiMesh.mNormals();
        if (normalsBuffer != null) {
            final List<GlFloat3> normals = processNormals(normalsBuffer);
            mesh.loadNormals(normals.toArray(new GlFloat3[0]));
        }

        final AIFace.Buffer indicesBuffer = aiMesh.mFaces();
        final List<GlUInt> indices = processIndices(indicesBuffer);
        mesh.loadIndices(indices.toArray(new GlUInt[0]));

        return mesh;
    }

    private static List<GlFloat3> processPositions(AIVector3D.Buffer buffer) {
        return AssimpUtil.process3D(buffer);
    }

    private static List<GlFloat3> processNormals(AIVector3D.Buffer buffer) {
        return AssimpUtil.process3D(buffer);
    }

    private static List<GlFloat3> process3D(AIVector3D.Buffer buffer) {
        final List<GlFloat3> vertices = new ArrayList<>(buffer.limit() / 3);
        while (buffer.hasRemaining()) {
            final AIVector3D value = buffer.get();
            final float x = value.x();
            final float y = value.y();
            final float z = value.z();
            vertices.add(GlFloat3.of(x, y, z));
        }
        return vertices;
    }

    private static List<GlFloat2> processUvCoords(AIVector3D.Buffer buffer) {
        final List<GlFloat2> vertices = new ArrayList<>(buffer.limit() / 2);
        while (buffer.hasRemaining()) {
            final AIVector3D value = buffer.get();
            final float x = value.x();
            final float y = value.y();
            vertices.add(GlFloat2.of(x, 1 - y));
        }
        return vertices;
    }

    private static List<GlUInt> processIndices(AIFace.Buffer facesBuffer) {
        final List<GlUInt> indices = new ArrayList<>(facesBuffer.limit() * 3);
        while (facesBuffer.hasRemaining()) {
            final AIFace aiFace = facesBuffer.get();
            final IntBuffer indicesBuffer = aiFace.mIndices();
            while (indicesBuffer.hasRemaining()) {
                final int value = indicesBuffer.get();
                indices.add(GlUInt.of(value));
            }
        }
        return indices;
    }
}

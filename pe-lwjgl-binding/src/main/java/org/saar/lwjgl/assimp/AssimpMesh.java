package org.saar.lwjgl.assimp;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.vaos.ReadOnlyVao;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.*;
import org.saar.lwjgl.opengl.primitive.*;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class AssimpMesh {

    private final Vao vao;
    private final DataBuffer positionVbo;
    private final DataBuffer uvCoordVbo;
    private final DataBuffer normalVbo;
    private final IndexBuffer indexVbo;

    private AssimpMesh(Vao vao, DataBuffer positionVbo, DataBuffer uvCoordVbo,
                       DataBuffer normalVbo, IndexBuffer indexVbo) {
        this.vao = vao;
        this.positionVbo = positionVbo;
        this.uvCoordVbo = uvCoordVbo;
        this.normalVbo = normalVbo;
        this.indexVbo = indexVbo;
    }

    public static AssimpMesh create() {
        return new AssimpMesh(Vao.create(),
                new DataBuffer(VboUsage.STATIC_DRAW),
                new DataBuffer(VboUsage.STATIC_DRAW),
                new DataBuffer(VboUsage.STATIC_DRAW),
                new IndexBuffer(VboUsage.STATIC_DRAW));
    }

    public void loadPositions(GlFloat3... positions) {
        AssimpMesh.load(this.positionVbo, positions);
    }

    public void loadUvCoords(GlFloat2... uvCoords) {
        AssimpMesh.load(this.uvCoordVbo, uvCoords);
    }

    public void loadNormals(GlFloat3... normals) {
        AssimpMesh.load(this.normalVbo, normals);
    }

    public void loadIndices(GlUInt... indices) {
        AssimpMesh.load(this.indexVbo, indices);
    }

    private static void load(IVbo vbo, GlPrimitive... primitives) {
        if (primitives.length > 0) {
            final int bytes = primitives.length * primitives[0].getSize();
            final ByteBuffer buffer = MemoryUtils.allocByte(bytes);
            final BufferWriter writer = new BufferWriter(buffer);
            GlPrimitives.write(writer, primitives);
            Vbos.allocateAndStore(vbo, buffer);
            MemoryUtil.memFree(buffer);
        }
    }

    public ReadOnlyVao getVao() {
        return this.vao;
    }

    public ReadOnlyVbo getPositionVbo() {
        return this.positionVbo;
    }

    public ReadOnlyVbo getUvCoordVbo() {
        return this.uvCoordVbo;
    }

    public ReadOnlyVbo getNormalVbo() {
        return this.normalVbo;
    }

    public ReadOnlyVbo getIndexVbo() {
        return this.indexVbo;
    }
}

package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;

public class MeshVertexBuffer extends MeshDataBuffer {

    public MeshVertexBuffer(IVbo vbo) {
        super(vbo);
    }

    private static MeshVertexBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, usage);
        return new MeshVertexBuffer(vbo);
    }

    public static MeshVertexBuffer createStatic() {
        return MeshVertexBuffer.create(VboUsage.STATIC_DRAW);
    }

    public static MeshVertexBuffer createDynamic() {
        return MeshVertexBuffer.create(VboUsage.DYNAMIC_DRAW);
    }
}

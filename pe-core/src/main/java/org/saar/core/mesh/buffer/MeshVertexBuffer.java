package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.vbo.IVbo;
import org.saar.lwjgl.opengl.vbo.Vbo;
import org.saar.lwjgl.opengl.vbo.VboTarget;
import org.saar.lwjgl.opengl.vbo.VboUsage;

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

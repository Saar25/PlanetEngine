package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.vbo.IVbo;
import org.saar.lwjgl.opengl.vbo.Vbo;
import org.saar.lwjgl.opengl.vbo.VboTarget;
import org.saar.lwjgl.opengl.vbo.VboUsage;

public class MeshInstanceBuffer extends MeshDataBuffer {

    public MeshInstanceBuffer(IVbo vbo) {
        super(vbo);
    }

    private static MeshInstanceBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, usage);
        return new MeshInstanceBuffer(vbo);
    }

    public static MeshInstanceBuffer createStatic() {
        return MeshInstanceBuffer.create(VboUsage.STATIC_DRAW);
    }

    public static MeshInstanceBuffer createDynamic() {
        return MeshInstanceBuffer.create(VboUsage.DYNAMIC_DRAW);
    }
}

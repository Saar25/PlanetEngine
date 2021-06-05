package org.saar.core.mesh.build.buffers;

import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;

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

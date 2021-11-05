package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.vaos.WritableVao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;

public class MeshIndexBuffer extends MeshBuffer {

    public MeshIndexBuffer(IVbo vbo) {
        super(vbo);
    }

    private static MeshIndexBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, usage);
        return new MeshIndexBuffer(vbo);
    }

    public static MeshIndexBuffer createStatic() {
        return MeshIndexBuffer.create(VboUsage.STATIC_DRAW);
    }

    public static MeshIndexBuffer createDynamic() {
        return MeshIndexBuffer.create(VboUsage.DYNAMIC_DRAW);
    }

    public void allocateCount(int count) {
        // TODO: Check if byte or short etc. can be used instead
        final int bytes = DataType.INT.getBytes();
        final int capacity = count * bytes;
        allocate(capacity);
    }

    @Override
    public void loadInVao(WritableVao vao) {
        vao.loadVbo(this.vbo);
    }
}
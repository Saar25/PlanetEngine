package org.saar.core.model.mesh.buffers;

import org.saar.core.model.mesh.MeshBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.opengl.objects.vbos.*;

public class MeshIndexBuffer extends MeshBuffer {

    private final IVbo vbo;

    public MeshIndexBuffer(IVbo vbo, VboWrapper wrapper) {
        super(wrapper);
        this.vbo = vbo;
    }

    private static MeshIndexBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, usage);
        return new MeshIndexBuffer(vbo, new VboWrapper(vbo));
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
    public void loadInVao(WriteableVao vao) {
        vao.loadVbo(this.vbo);
    }
}
package org.saar.core.mesh.build.buffers;

import org.saar.core.mesh.build.MeshBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;

public class MeshIndexBuffer extends MeshBuffer {

    private final IVbo vbo;

    public MeshIndexBuffer(IVbo vbo, BufferObjectWrapper wrapper) {
        super(wrapper);
        this.vbo = vbo;
    }

    private static MeshIndexBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, usage);
        return new MeshIndexBuffer(vbo, new BufferObjectWrapper(vbo));
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
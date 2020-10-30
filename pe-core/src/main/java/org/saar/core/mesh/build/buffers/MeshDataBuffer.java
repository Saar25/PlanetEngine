package org.saar.core.mesh.build.buffers;

import org.saar.core.mesh.build.MeshBuffer;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeshDataBuffer extends MeshBuffer {

    private final List<Attribute> attributes = new ArrayList<>();
    private final IVbo vbo;

    public MeshDataBuffer(IVbo vbo, BufferObjectWrapper wrapper) {
        super(wrapper);
        this.vbo = vbo;
    }

    public void allocateCount(int count) {
        final int bytes = Attribute.sumBytes(attributesArray());
        final int capacity = count * bytes;
        allocate(capacity);
    }

    @Override
    public void loadInVao(WriteableVao vao) {
        vao.loadVbo(this.vbo, attributesArray());
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(Attribute... attributes) {
        Collections.addAll(this.attributes, attributes);
    }

    private Attribute[] attributesArray() {
        return this.attributes.toArray(new Attribute[0]);
    }
}

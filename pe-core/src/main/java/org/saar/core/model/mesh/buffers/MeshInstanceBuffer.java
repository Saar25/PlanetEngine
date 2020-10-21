package org.saar.core.model.mesh.buffers;

import org.saar.core.model.mesh.MeshBuffer;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.opengl.objects.vbos.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeshInstanceBuffer extends MeshBuffer {

    private final List<Attribute> attributes = new ArrayList<>();
    private final IVbo vbo;

    public MeshInstanceBuffer(IVbo vbo, BufferObjectWrapper wrapper) {
        super(wrapper);
        this.vbo = vbo;
    }

    private static MeshInstanceBuffer create(VboUsage usage) {
        final IVbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, usage);
        return new MeshInstanceBuffer(vbo, new BufferObjectWrapper(vbo));
    }

    public static MeshInstanceBuffer createStatic() {
        return MeshInstanceBuffer.create(VboUsage.STATIC_DRAW);
    }

    public static MeshInstanceBuffer createDynamic() {
        return MeshInstanceBuffer.create(VboUsage.DYNAMIC_DRAW);
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

package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;
import org.saar.lwjgl.opengl.vaos.WritableVao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeshDataBuffer extends MeshBuffer {

    private final List<IAttribute> attributes = new ArrayList<>();

    public MeshDataBuffer(IVbo vbo) {
        super(vbo);
    }

    public void allocateCount(int count) {
        final int bytes = Attributes.sumBytes(attributesArray());
        final int capacity = count * bytes;
        allocate(capacity);
    }

    public void update(int from, int amount) {
        final int bytes = Attributes.sumBytes(attributesArray());
        this.buffer.position(from * bytes).limit((from + amount) * bytes);
        this.vbo.store((long) from * bytes, this.buffer.asByteBuffer());
    }

    public void setPosition(int position) {
        final int bytes = Attributes.sumBytes(attributesArray());
        this.buffer.clear().position(position * bytes);
    }

    @Override
    public void loadInVao(WritableVao vao) {
        vao.loadVbo(this.vbo, attributesArray());
    }

    public void addAttribute(IAttribute attribute) {
        this.attributes.add(attribute);
    }

    public void addAttributes(IAttribute... attributes) {
        Collections.addAll(this.attributes, attributes);
    }

    private IAttribute[] attributesArray() {
        return this.attributes.toArray(new IAttribute[0]);
    }
}

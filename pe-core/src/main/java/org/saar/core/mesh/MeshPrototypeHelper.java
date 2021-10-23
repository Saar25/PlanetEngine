package org.saar.core.mesh;

import org.saar.core.mesh.buffer.*;
import org.saar.lwjgl.opengl.objects.vaos.WritableVao;

import java.util.List;

public class MeshPrototypeHelper {

    private final MeshBufferLocator locator;

    private List<MeshBuffer> buffers;
    private List<MeshIndexBuffer> indexBuffers;
    private List<MeshVertexBuffer> vertexBuffers;
    private List<MeshInstanceBuffer> instanceBuffers;

    public MeshPrototypeHelper(MeshPrototype prototype) {
        this.locator = new MeshBufferLocator(prototype);
    }

    public void loadToVao(WritableVao vao) {
        for (MeshBuffer buffer : getBuffers()) {
            buffer.loadInVao(vao);
        }
    }

    public void allocateInstances(int instances) {
        for (MeshInstanceBuffer buffer : getInstanceBuffers()) {
            buffer.allocateCount(instances);
        }
    }

    public void allocateVertices(int vertices) {
        for (MeshVertexBuffer buffer : getVertexBuffers()) {
            buffer.allocateCount(vertices);
        }
    }

    public void allocateIndices(int indices) {
        for (MeshIndexBuffer buffer : getIndexBuffers()) {
            buffer.allocateCount(indices);
        }
    }

    public void store() {
        for (MeshBuffer buffer : getBuffers()) {
            buffer.store(0);
        }
    }

    private List<MeshBuffer> getBuffers() {
        if (this.buffers == null) {
            this.buffers = this.locator.getMeshBuffers();
        }
        return this.buffers;
    }

    private List<MeshInstanceBuffer> getInstanceBuffers() {
        if (this.instanceBuffers == null) {
            this.instanceBuffers = this.locator.getMeshInstanceBuffers();
        }
        return this.instanceBuffers;
    }

    private List<MeshVertexBuffer> getVertexBuffers() {
        if (this.vertexBuffers == null) {
            this.vertexBuffers = this.locator.getMeshVertexBuffers();
        }
        return this.vertexBuffers;
    }

    private List<MeshIndexBuffer> getIndexBuffers() {
        if (this.indexBuffers == null) {
            this.indexBuffers = this.locator.getMeshIndexBuffers();
        }
        return this.indexBuffers;
    }
}

package org.saar.core.model.mesh;

import org.saar.core.model.Vertex;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;

import java.util.List;

public class MeshPrototypeHelper {

    private final MeshBufferLocator locator;

    private List<MeshBuffer> buffers;
    private List<MeshIndexBuffer> indexBuffers;
    private List<MeshVertexBuffer> vertexBuffers;

    public MeshPrototypeHelper(MeshPrototype prototype) {
        this.locator = new MeshBufferLocator(prototype);
    }

    public void loadToVao(WriteableVao vao) {
        for (MeshBuffer buffer : getBuffers()) {
            buffer.loadInVao(vao);
        }
    }

    public void allocateVertices(Vertex[] vertices) {
        for (MeshVertexBuffer buffer : getVertexBuffers()) {
            buffer.allocateCount(vertices.length);
        }
    }

    public void allocateIndices(int[] indices) {
        for (MeshIndexBuffer buffer : getIndexBuffers()) {
            buffer.allocateCount(indices.length);
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

    private List<MeshIndexBuffer> getIndexBuffers() {
        if (this.indexBuffers == null) {
            this.indexBuffers = this.locator.getMeshIndexBuffers();
        }
        return this.indexBuffers;
    }

    private List<MeshVertexBuffer> getVertexBuffers() {
        if (this.vertexBuffers == null) {
            this.vertexBuffers = this.locator.getMeshVertexBuffers();
        }
        return this.vertexBuffers;
    }
}

package org.saar.core.mesh.build;

import org.saar.core.mesh.Instance;
import org.saar.core.mesh.Vertex;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshInstanceBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;

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

    public void loadToVao(WriteableVao vao) {
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

    public void allocateInstances(Instance[] instances) {
        allocateInstances(instances.length);
    }

    public void allocateVertices(Vertex[] vertices) {
        allocateVertices(vertices.length);
    }

    public void allocateIndices(int[] indices) {
        allocateIndices(indices.length);
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
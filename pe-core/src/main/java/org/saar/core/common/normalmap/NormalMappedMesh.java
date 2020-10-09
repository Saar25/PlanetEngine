package org.saar.core.common.normalmap;


import org.lwjgl.system.MemoryUtil;
import org.saar.core.model.Mesh;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.*;

import java.nio.ByteBuffer;

public class NormalMappedMesh implements Mesh {

    private final Mesh mesh;

    public NormalMappedMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public static NormalMappedMesh load(NormalMappedVertex[] vertices, int[] indices) {
        final NormalMappedModelBuffers buffers = NormalMappedModelBuffers
                .singleDataBuffer(vertices.length, indices.length);
        buffers.load(vertices, indices);
        return new NormalMappedMesh(buffers.getMesh());
    }

    public static NormalMappedMesh load(String objFile) throws Exception {
        try (final AssimpMesh assimpMesh = AssimpUtil.load(objFile)) {
            final ByteBuffer dataBuffer = assimpMesh.allocateByteBuffer(
                    new AssimpPositionComponent(),
                    new AssimpTexCoordComponent(0),
                    new AssimpNormalComponent(),
                    new AssimpTangentComponent(),
                    new AssimpBiTangentComponent());
            final ByteBuffer indexBuffer = assimpMesh.allocateIndexBuffer();

            final Mesh mesh = NormalMappedModelBuffers.toMesh(dataBuffer, indexBuffer);

            MemoryUtil.memFree(dataBuffer);
            MemoryUtil.memFree(indexBuffer);

            return new NormalMappedMesh(mesh);
        }
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}

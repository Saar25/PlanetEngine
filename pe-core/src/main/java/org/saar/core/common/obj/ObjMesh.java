package org.saar.core.common.obj;


import org.lwjgl.system.MemoryUtil;
import org.saar.core.model.Mesh;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.AssimpNormalComponent;
import org.saar.lwjgl.assimp.component.AssimpPositionComponent;
import org.saar.lwjgl.assimp.component.AssimpTexCoordComponent;

import java.nio.ByteBuffer;

public class ObjMesh implements Mesh {

    private final Mesh mesh;

    public ObjMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public static ObjMesh load(ObjVertex[] vertices, int[] indices) {
        final ObjModelBuffers buffers = ObjModelBuffers
                .singleDataBuffer(vertices.length, indices.length);
        buffers.load(vertices, indices);
        return new ObjMesh(buffers.getMesh());
    }

    public static ObjMesh load(String objFile) throws Exception {
        try (final AssimpMesh assimpMesh = AssimpUtil.load(objFile)) {
            final ByteBuffer dataBuffer = assimpMesh.allocateByteBuffer(
                    new AssimpPositionComponent(),
                    new AssimpTexCoordComponent(0),
                    new AssimpNormalComponent());
            final ByteBuffer indexBuffer = assimpMesh.allocateIndexBuffer();

            final Mesh mesh = ObjModelBuffers.toMesh(dataBuffer, indexBuffer);

            MemoryUtil.memFree(dataBuffer);
            MemoryUtil.memFree(indexBuffer);

            return new ObjMesh(mesh);
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

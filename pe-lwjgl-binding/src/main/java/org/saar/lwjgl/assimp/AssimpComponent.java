package org.saar.lwjgl.assimp;

import org.lwjgl.assimp.AIMesh;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class AssimpComponent {

    public abstract int bytes();

    public abstract void init(AIMesh aiMesh);

    public abstract void next(BufferWriter writer);

    public static int bytes(AssimpComponent... components) {
        int bytes = 0;
        for (AssimpComponent component : components) {
            bytes += component.bytes();
        }
        return bytes;
    }

}

package org.saar.lwjgl.assimp;

import org.lwjgl.assimp.AIMesh;

public abstract class AssimpComponent {

    public abstract void init(AIMesh aiMesh);

    public abstract void next();

}

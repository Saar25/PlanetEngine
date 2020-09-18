package org.saar.core.renderer.deferred;

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface RenderPass {

    void render(ReadOnlyTexture image);

    void delete();

}

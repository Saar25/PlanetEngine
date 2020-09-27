package org.saar.core.renderer;

import org.saar.lwjgl.opengl.utils.GlCullFace;

public class RenderingHintsBase implements RenderingHints {

    public GlCullFace cullFace = GlCullFace.BACK;

    @Override
    public GlCullFace getCullFace() {
        return this.cullFace;
    }
}

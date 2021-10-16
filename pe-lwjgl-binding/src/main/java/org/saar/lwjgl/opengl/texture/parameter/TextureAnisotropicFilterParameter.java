package org.saar.lwjgl.opengl.texture.parameter;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TextureAnisotropicFilterParameter extends TextureFloatParameterParameter implements TextureParameter {

    private static final int pName = EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;

    private static final int maxAnisotropyFilterId = EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
    private static final float maxAnisotropyFilter = GL11.glGetFloat(maxAnisotropyFilterId);

    public TextureAnisotropicFilterParameter(float lodBias) {
        super(pName, lodBias);
        requireCapability(lodBias);
    }

    private static void requireCapability(float lodBias) {
        if (!GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            System.err.println("Anisotropic filtering is not supported");
        } else if (lodBias > TextureAnisotropicFilterParameter.maxAnisotropyFilter) {
            System.err.println("Lod bias of Anisotropic filtering cannot be greater than " +
                    TextureAnisotropicFilterParameter.maxAnisotropyFilter);
        }
    }
}

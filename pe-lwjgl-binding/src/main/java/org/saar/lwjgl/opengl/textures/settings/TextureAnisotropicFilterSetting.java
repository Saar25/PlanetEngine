package org.saar.lwjgl.opengl.textures.settings;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TextureAnisotropicFilterSetting extends TextureFloatParameterSetting implements TextureSetting {

    private static final int pName = EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
    private static float maxAnisotropyFilter;

    public TextureAnisotropicFilterSetting(float lodBias) {
        super(pName, lodBias);
        requireCapability(lodBias);
    }

    private static void requireCapability(float lodBias) {
        if (!GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            System.err.println("Anisotropic filtering is not supported");
        } else if (lodBias > TextureAnisotropicFilterSetting.getMaxAnisotropyFilter()) {
            System.err.println("Lod bias of Anisotropic filtering cannot be greater than " +
                    TextureAnisotropicFilterSetting.getMaxAnisotropyFilter());
        }
    }

    private static float getMaxAnisotropyFilter() {
        if (TextureAnisotropicFilterSetting.maxAnisotropyFilter == 0) {
            final int name = EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
            TextureAnisotropicFilterSetting.maxAnisotropyFilter = GL11.glGetFloat(name);
        }
        return TextureAnisotropicFilterSetting.maxAnisotropyFilter;
    }
}

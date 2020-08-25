package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface IFbo {

    /**
     * Blit the fbo to the given fbo
     */
    void blitFbo(IFbo fbo);

    void blitFbo(IFbo fbo, MagFilterParameter filter, GlBuffer... buffers);

    /**
     * Returns the width of the fbo
     *
     * @return the width
     */
    int getWidth();

    /**
     * Returns the height of the fbo
     *
     * @return the height
     */
    int getHeight();

    /**
     * Bind the fbo
     */
    void bind();

    /**
     * Bind the fbo to the given target
     *
     * @param target the fbo target
     */
    void bind(FboTarget target);

    /**
     * Unbind the fbo
     */
    void unbind();

    /**
     * Unbind the fbo from the given target
     *
     * @param target the fbo target
     */
    void unbind(FboTarget target);

    /**
     * Delete the fbo
     */
    void delete();

}

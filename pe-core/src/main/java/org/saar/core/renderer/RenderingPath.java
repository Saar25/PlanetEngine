package org.saar.core.renderer;

import org.saar.core.renderer.renderpass.RenderPassBuffers;

public interface RenderingPath<T extends RenderPassBuffers> {

    RenderingOutput<T> render();

    void delete();

}

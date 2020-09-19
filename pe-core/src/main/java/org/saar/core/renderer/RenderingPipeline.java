package org.saar.core.renderer;

import org.saar.core.screen.Screen;

public interface RenderingPipeline {

    void render(Screen drawScreen);

    void delete();

}

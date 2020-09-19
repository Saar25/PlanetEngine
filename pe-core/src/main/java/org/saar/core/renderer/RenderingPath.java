package org.saar.core.renderer;

import org.saar.core.screen.Screen;

public interface RenderingPath {

    void render(Screen drawScreen);

    void delete();

}

package org.saar.core.renderer;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;

public interface RenderingOutput {

    void to(Screen screen);

    ReadOnlyTexture toTexture();

    default void toMainScreen() {
        to(MainScreen.INSTANCE);
    }

}

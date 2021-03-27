package org.saar.core.renderer;

import org.saar.core.screen.MainScreen;
import org.saar.core.screen.Screen;

public interface RenderingOutput {

    void to(Screen screen);

    default void toMainScreen() {
        to(MainScreen.getInstance());
    }

}

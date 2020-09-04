package org.saar.core.screen;

import org.saar.lwjgl.opengl.fbos.DrawableFbo;

public interface Screen {

    void copyTo(Screen other);

    DrawableFbo getFbo();

    void setAsDraw();

    void setAsRead();

}

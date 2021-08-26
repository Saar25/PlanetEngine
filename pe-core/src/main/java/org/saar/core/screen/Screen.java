package org.saar.core.screen;

import org.saar.lwjgl.opengl.fbos.FboBlitFilter;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public interface Screen {

    int getWidth();

    int getHeight();

    default void copyTo(Screen other) {
        copyTo(other, FboBlitFilter.LINEAR, GlBuffer.COLOUR);
    }

    void copyTo(Screen other, FboBlitFilter filter, GlBuffer... buffers);

    void setAsDraw();

    void setAsRead();

}

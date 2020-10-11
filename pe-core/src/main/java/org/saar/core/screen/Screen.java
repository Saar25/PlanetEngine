package org.saar.core.screen;

public interface Screen {

    int getWidth();

    int getHeight();

    void copyTo(Screen other);

    void setAsDraw();

    void setAsRead();

}

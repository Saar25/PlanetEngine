package org.saar.core.screen;

public interface Screen {
    
    void resize(int width, int height);

    void setAsDraw();

    void setAsRead();

    void delete();
    
}

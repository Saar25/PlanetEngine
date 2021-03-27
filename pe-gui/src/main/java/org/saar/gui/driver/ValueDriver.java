package org.saar.gui.driver;

public interface ValueDriver<T> {

    void update();

    T getValue();

}

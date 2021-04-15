package org.saar.gui;

import java.util.Arrays;
import java.util.List;

/**
 * This class represent a ui component, with out any logic
 * use this class to group UIObjects and render them as one
 */
public class UIComponent {

    private final List<UIObject> uiObjects;

    public UIComponent(UIObject... uiObjects) {
        this.uiObjects = Arrays.asList(uiObjects);
    }

    public List<UIObject> getUiObjects() {
        return this.uiObjects;
    }
}

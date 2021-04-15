package org.saar.gui;

import org.saar.gui.style.Style;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a ui component, with out any logic
 * use this class to group UIObjects and render them as one
 */
public abstract class UIComponent {

    private final Style style = new Style();

    private final List<UIObject> uiObjects = new ArrayList<>();

    protected void add(UIObject uiObject) {
        this.uiObjects.add(uiObject);
    }

    public List<UIObject> getUiObjects() {
        return this.uiObjects;
    }

    public Style getStyle() {
        return this.style;
    }
}

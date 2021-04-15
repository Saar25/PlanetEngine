package org.saar.gui.component;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.UIComponent;
import org.saar.gui.UIObject;
import org.saar.gui.event.EventHandler;
import org.saar.gui.event.MouseEvent;

public class UIButton extends UIComponent {

    private final BooleanProperty pressedProperty = new SimpleBooleanProperty();

    private final UIObject uiObject = new UIObject();

    private EventHandler<MouseEvent> onAction = e -> {};

    public UIButton() {
        initUiObject();
    }

    private void initUiObject() {
        add(this.uiObject);
    }

    public BooleanProperty pressedProperty() {
        return this.pressedProperty;
    }

    public void setOnAction(EventHandler<MouseEvent> onAction) {
        this.onAction = onAction;
    }

    @Override
    public void onMousePress(MouseEvent event) {
        if (event.getButton().isPrimary()) {
            pressedProperty().set(true);
            getStyle().colourModifier.set(1.5f, 1.5f, 1.5f, 1f);
            for (UIObject child : getUiObjects()) {
                child.getStyle().colourModifier.set(getStyle().colourModifier);
            }
        }
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        if (event.getButton().isPrimary()) {
            if (pressedProperty().get()) {
                pressedProperty().set(false);
                this.onAction.handle(event);
            }
            getStyle().colourModifier.set(1);
            for (UIObject child : getUiObjects()) {
                child.getStyle().colourModifier.set(getStyle().colourModifier);
            }
        }
    }

    @Override
    public void onMouseEnter(MouseEvent event) {
    }

    @Override
    public void onMouseExit(MouseEvent event) {
        pressedProperty().set(false);
        getStyle().colourModifier.set(1f);
        for (UIObject child : getUiObjects()) {
            child.getStyle().colourModifier.set(getStyle().colourModifier);
        }
    }
}
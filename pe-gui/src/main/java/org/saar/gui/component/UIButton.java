package org.saar.gui.component;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.UIBlock;
import org.saar.gui.UIComponent;
import org.saar.gui.event.EventHandler;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.style.Colours;

public class UIButton extends UIComponent {

    private final BooleanProperty pressedProperty = new SimpleBooleanProperty();

    private final UIBlock uiBlock = new UIBlock();

    private EventHandler<MouseEvent> onAction = e -> {};

    public UIButton() {
        initUiObject();
    }

    private void initUiObject() {
        this.uiBlock.getStyle().getBackgroundColour().set(Colours.GREY);
        this.uiBlock.getStyle().getBorderColour().set(Colours.DARK_GREY);
        this.uiBlock.getStyle().getBorders().set(2);
        add(this.uiBlock);
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
            getStyle().getColourModifier().set(1.5f);
        }
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        if (event.getButton().isPrimary()) {
            if (pressedProperty().get()) {
                pressedProperty().set(false);
                this.onAction.handle(event);
            }
            getStyle().getColourModifier().set(1);
        }
    }

    @Override
    public void onMouseEnter(MouseEvent event) {
    }

    @Override
    public void onMouseExit(MouseEvent event) {
        pressedProperty().set(false);
        getStyle().getColourModifier().set(1);
    }
}

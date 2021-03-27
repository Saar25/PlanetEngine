package org.saar.gui.control;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.GuiController;
import org.saar.gui.GuiObject;
import org.saar.gui.event.EventHandler;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.objects.TImage;
import org.saar.gui.objects.TRectangle;

public class GuiButton extends GuiController {

    private static final int g = 5;

    private final BooleanProperty pressedProperty = new SimpleBooleanProperty();

    private EventHandler<MouseEvent> onAction = e -> {
    };

    private GuiObject guiObject = new TRectangle();

    public GuiButton() {
        getChildren().add(guiObject);
    }

    public void setImage(TImage image) {
        getChildren().remove(guiObject);
        getChildren().add(image);
        guiObject = image;
    }

    public BooleanProperty pressedProperty() {
        return pressedProperty;
    }

    public void setOnAction(EventHandler<MouseEvent> onAction) {
        this.onAction = onAction;
    }

    @Override
    public void onMousePress(MouseEvent event) {
        if (event.getButton().isPrimary()) {
            pressedProperty().set(true);
            getStyle().colourModifier.set(1.5f, 1.5f, 1.5f, 1f);
            for (GuiObject child : getChildren()) {
                child.getStyle().colourModifier.set(getStyle().colourModifier);
            }
        }
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        if (event.getButton().isPrimary()) {
            if (pressedProperty().get()) {
                pressedProperty().set(false);
                onAction.handle(event);
            }
            getStyle().colourModifier.set(1);
            for (GuiObject child : getChildren()) {
                child.getStyle().colourModifier.set(getStyle().colourModifier);
            }
        }
    }

    @Override
    public void onMouseEnter(MouseEvent event) {
        //getStyle().position.add(-g, -g);
        //getStyle().dimensions.add(2 * g, 2 * g);
    }

    @Override
    public void onMouseExit(MouseEvent event) {
        pressedProperty().set(false);
        //getStyle().position.add(g, g);
        //getStyle().dimensions.add(-2 * g, -2 * g);
        getStyle().colourModifier.set(1f);
        for (GuiObject child : getChildren()) {
            child.getStyle().colourModifier.set(getStyle().colourModifier);
        }
    }
}

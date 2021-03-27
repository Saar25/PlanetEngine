package org.saar.gui.control;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.GuiController;
import org.saar.gui.GuiObject;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.objects.TImage;
import org.saar.gui.objects.TRectangle;

public class GuiCheckBox extends GuiController {

    private final BooleanProperty checkedProperty = new SimpleBooleanProperty();

    private final GuiObject box = new TRectangle();
    private final int space = 5;
    private GuiObject check = new TRectangle();

    public GuiCheckBox() {
        getChildren().add(box);

        check.getStyle().borders.set(0);
        check.getStyle().x.set(space);
        check.getStyle().y.set(space);
        check.getStyle().width.set(10);
        check.getStyle().height.set(10);
        check.getStyle().backgroundColour.set(255, 255, 255, 255);

        checkedProperty().addListener(e -> {
            if (e.getNewValue()) {
                getChildren().add(check);
            } else {
                getChildren().remove(check);
            }
        });
    }

    public BooleanProperty checkedProperty() {
        return checkedProperty;
    }

    public void setCheckImage(TImage check) {
        if (checkedProperty().get()) {
            getChildren().remove(this.check);
            getChildren().add(check);
        }
        this.check = check;
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        final boolean flip = !checkedProperty().get();
        checkedProperty().set(flip);
    }
}

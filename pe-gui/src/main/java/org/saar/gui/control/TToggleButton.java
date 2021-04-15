package org.saar.gui.control;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.UIController;
import org.saar.gui.UIObject;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.objects.TRectangle;
import org.saar.utils.Smooth;

public class TToggleButton extends UIController {

    private final BooleanProperty valueProperty = new SimpleBooleanProperty();
    private final Smooth<Float> togglePosition = Smooth.ofFloat(10);

    private final UIObject bar;
    private final UIObject toggle;

    public TToggleButton(int x, int y) {
        this();
        getStyle().x.set(x);
        getStyle().y.set(y);
        getStyle().width.set(300);
        getStyle().height.set(100);
        getStyle().radiuses.set(15);
    }

    public TToggleButton() {
        this.bar = createBar();
        this.toggle = createToggle();

        getChildren().add(bar);
        getChildren().add(toggle);

        togglePosition.set(0f, true);

        valueProperty().addListener(e -> {
            if (e.getNewValue()) {
                togglePosition.setTarget(1f);
                bar.getStyle().backgroundColour.set(0, 255, 0, 255);
            } else {
                togglePosition.setTarget(0f);
                bar.getStyle().backgroundColour.set(255, 0, 0, 255);
            }
        });
        valueProperty().setValue(false);
    }

    private static UIObject createBar() {
        final UIObject rectangle = new TRectangle();
        rectangle.getStyle().backgroundColour.set(255, 0, 0, 255);
        return rectangle;
    }

    private static UIObject createToggle() {
        final UIObject rectangle = new TRectangle();
        rectangle.getStyle().backgroundColour.set(255, 255, 255, 255);
        rectangle.getStyle().width.set(30);
        return rectangle;
    }

    public BooleanProperty valueProperty() {
        return valueProperty;
    }

    @Override
    public void onMousePress(MouseEvent event) {
        final boolean value = valueProperty().getValue();
        valueProperty().setValue(!value);
    }

    public void update() {
        togglePosition.interpolate(1);
        final int w1 = getStyle().width.get();
        final int w2 = toggle.getStyle().width.get();
        toggle.getStyle().x.set((int) (togglePosition.get() * (w1 - w2)));
    }
}

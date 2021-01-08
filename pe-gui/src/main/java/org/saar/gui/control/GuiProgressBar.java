package org.saar.gui.control;

import org.jproperty.type.FloatProperty;
import org.jproperty.type.SimpleFloatProperty;
import org.saar.gui.GuiController;
import org.saar.gui.GuiObject;
import org.saar.gui.driver.FloatDriver;
import org.saar.gui.objects.TRectangle;
import org.saar.gui.style.property.Colours;

public class GuiProgressBar extends GuiController {

    private final FloatProperty progressProperty = new SimpleFloatProperty();

    private final GuiObject bar = createBar();
    private final GuiObject border = createBorder();

    private FloatDriver progressDriver = null;

    public GuiProgressBar() {
        getChildren().add(bar);
        getChildren().add(border);

        getStyle().backgroundColour.setNormalized(0, 1, 0);
        getStyle().x.set(100);
        getStyle().y.set(200);
        getStyle().width.set(500);
        getStyle().height.set(200);

        progressProperty().addListener(e ->
                bar.getStyle().width.set(20));
    }

    private static GuiObject createBar() {
        return new TRectangle();
    }

    private static GuiObject createBorder() {
        final TRectangle border = new TRectangle();
        border.getStyle().backgroundColour.set(0, 0, 0, 0);
        border.getStyle().borderColour.set(Colours.BLACK);
        border.getStyle().borders.set(3);
        return border;
    }

    public void update() {
        if (progressDriver != null) {
            progressDriver.update();
            progressProperty().set(progressDriver.get());
        }
    }

    public FloatProperty progressProperty() {
        return progressProperty;
    }

    public float getValue() {
        return progressProperty().get();
    }

    public void setValue(float value) {
        progressProperty().set(value);
    }

    public void setProgressDriver(FloatDriver progressDriver) {
        this.progressDriver = progressDriver;
    }
}

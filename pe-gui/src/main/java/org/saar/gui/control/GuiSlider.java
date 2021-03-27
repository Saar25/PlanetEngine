package org.saar.gui.control;

import org.jproperty.type.FloatProperty;
import org.jproperty.type.SimpleFloatProperty;
import org.saar.gui.GuiController;
import org.saar.gui.GuiObject;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.objects.TRectangle;
import org.saar.gui.style.property.Colours;
import org.saar.gui.style.property.Dimensions;
import org.saar.gui.style.property.Position;
import org.saar.maths.utils.Maths;

public class GuiSlider extends GuiController {

    private final FloatProperty value = new SimpleFloatProperty(0);
    private final FloatProperty dynamicValue = new SimpleFloatProperty(0);

    private final FloatProperty min = new SimpleFloatProperty(0);
    private final FloatProperty max = new SimpleFloatProperty(100);

    private final GuiObject bar;
    private final GuiObject slider;

    public GuiSlider(int x, int y) {
        this();
        getStyle().x.set(x);
        getStyle().y.set(y);
    }

    public GuiSlider() {
        getChildren().add(this.bar = createBar());
        getChildren().add(this.slider = createSlider());

        valueProperty().addListener(e ->
                dynamicValueProperty().setValue(e.getNewValue()));
    }

    private GuiObject createBar() {
        return new TRectangle();
    }

    private GuiObject createSlider() {
        final TRectangle rectangle = new TRectangle();
        rectangle.getStyle().backgroundColour.set(Colours.WHITE);
        rectangle.getStyle().width.set(20);
        return rectangle;
    }

    @Override
    public void onMousePress(MouseEvent event) {
        onMouseDrag(event);
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        float value = getValue();
        valueProperty().setValue(value);
    }

    @Override
    public void onMouseDrag(MouseEvent event) {
        final Position position = getBar().getStyle().position;
        final Dimensions dimensions = getBar().getStyle().dimensions;
        int w = getSlider().getStyle().width.get();

        int xMax = dimensions.width.get() - w / 2;
        int x = Maths.clamp(event.getX() - position.x.get(), w / 2, xMax);

        getSlider().getStyle().bounds.setMiddleX(x);

        float value = getValue();
        dynamicValueProperty().setValue(value);
    }

    private float getValue() {
        int x1 = getSlider().getStyle().position.x.get();
        int w1 = getSlider().getStyle().dimensions.width.get();
        int x2 = getBar().getStyle().position.x.get();
        int w2 = getBar().getStyle().dimensions.width.get();
        float value = (float) (x1 - x2) / (w2 - w1);
        float range = max.getValue() - min.getValue();
        return value * range + min.getValue();
    }

    public void setValue(float value) {
        valueProperty().setValue(value);
    }

    public void update() {
        final float value = dynamicValueProperty().getValue();
        final float range = maxProperty().getValue() - minProperty().getValue();
        final float relative = (value - minProperty().getValue()) / range;
        final int width = (getBar().getStyle().dimensions.width.get() -
                getSlider().getStyle().dimensions.width.get());
        final int center = (int) (relative * width);

        getSlider().getStyle().bounds.setMiddleX(
                getSlider().getStyle().dimensions.width.get() / 2 + center);
    }

    public GuiObject getBar() {
        return bar;
    }

    public GuiObject getSlider() {
        return slider;
    }


    public FloatProperty valueProperty() {
        return value;
    }

    public FloatProperty dynamicValueProperty() {
        return dynamicValue;
    }

    public void setBounds(float min, float max) {
        setMin(min);
        setMax(max);
    }

    public FloatProperty maxProperty() {
        return max;
    }

    public float getMax() {
        return maxProperty().getValue();
    }

    public void setMax(float max) {
        maxProperty().setValue(max);
    }

    public FloatProperty minProperty() {
        return min;
    }

    public float getMin() {
        return minProperty().getValue();
    }

    public void setMin(float max) {
        minProperty().setValue(max);
    }
}

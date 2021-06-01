package org.saar.gui.component;

import org.jproperty.type.FloatProperty;
import org.jproperty.type.SimpleFloatProperty;
import org.saar.gui.block.UIBlock;
import org.saar.gui.UIComponent;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.style.Colours;
import org.saar.gui.style.value.CoordinateValues;
import org.saar.maths.utils.Maths;

public class UISlider extends UIComponent {

    private final FloatProperty valueProperty = new SimpleFloatProperty(0);
    private final FloatProperty dynamicValueProperty = new SimpleFloatProperty(0);

    private final FloatProperty min = new SimpleFloatProperty(0);
    private final FloatProperty max = new SimpleFloatProperty(100);

    private final UIBlock uiTruck = new UIBlock();
    private final UIBlock uiThumb = new UIBlock();

    public UISlider() {
        initUiTruck();
        initUiThumb();
    }

    private void initUiTruck() {
        this.uiTruck.getStyle().getBackgroundColour().set(Colours.GREY);
        this.uiTruck.getStyle().getBorderColour().set(Colours.DARK_GREY);
        this.uiTruck.getStyle().getBorders().set(2);
        add(this.uiTruck);
    }

    private void initUiThumb() {
        this.uiThumb.getStyle().getBackgroundColour().set(Colours.DARK_GREY);
        this.uiThumb.getStyle().getWidth().set(20);
        add(this.uiThumb);
    }

    public FloatProperty valueProperty() {
        return this.valueProperty;
    }

    public FloatProperty dynamicValueProperty() {
        return this.dynamicValueProperty;
    }

    @Override
    public void onMousePress(MouseEvent event) {
        onMouseDrag(event);
    }

    @Override
    public void onMouseRelease(MouseEvent event) {
        final float value = dynamicValueProperty().get();
        valueProperty().set(value);
    }

    @Override
    public void onMouseDrag(MouseEvent event) {
        final int x1 = this.uiTruck.getStyle().getX().get();
        final int w1 = this.uiTruck.getStyle().getWidth().get();
        final int w2 = this.uiThumb.getStyle().getWidth().get();
        final int x2 = Maths.clamp(event.getX(), x1 + w2 / 2, x1 + w1 - w2 / 2);

        final int xMax = x1 + w1;

        float normalized = (float) (x2 - x1) / (xMax - x1);

        this.uiThumb.getStyle().getX().set(
                CoordinateValues.percentCenter(normalized * 100));

        dynamicValueProperty().set(normalized * this.max.get() + this.min.get());
    }
}

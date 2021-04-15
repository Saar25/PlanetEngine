package org.saar.gui;

import org.jproperty.type.BooleanProperty;
import org.jproperty.type.SimpleBooleanProperty;
import org.saar.gui.event.MouseEvent;
import org.saar.gui.style.Style;
import org.saar.gui.style.Styleable;
import org.saar.gui.style.property.Colours;

/**
 * This class represent a panel that gui components can be placed on
 *
 * @author Saar ----
 * @version 1.2
 * @since 17.2.2019
 */
public class UIPanel extends UIContainer implements Styleable {

    private static final int HEADER = 20;
    private static final int BORDERS = 3;

    private final PanelBackground panelBackground;

    private final Style childrenStyle = new Style();

    private final BooleanProperty draggableProperty = new SimpleBooleanProperty(true);

    public UIPanel() {
        final PanelHeader panelHeader = new PanelHeader();
        getChildren().add(panelHeader);
        panelHeader.getStyle().width.set(500);
        panelHeader.getStyle().height.set(500);
        panelHeader.getStyle().x.set(30);
        panelHeader.getStyle().y.set(30);
        panelHeader.getStyle().backgroundColour.set(Colours.BLUE);

        this.panelBackground = new PanelBackground();
        getChildren().add(panelBackground);
    }

    @Override
    public Style getStyle() {
        return panelBackground.getStyle();
    }

    public Style getChildrenStyle() {
        return childrenStyle;
    }

    public BooleanProperty draggableProperty() {
        return draggableProperty;
    }

    private static class PanelBackground extends UIController {
        private PanelBackground() {
            getChildren().add(new UIObject());
            setSelectable(false);
        }
    }

    private class PanelHeader extends PanelBackground {

        private int xStart;
        private int yStart;

        @Override
        public void onMousePress(MouseEvent event) {
            this.xStart = event.getX() - UIPanel.this.getStyle().x.get();
            this.yStart = event.getY() - UIPanel.this.getStyle().y.get();
        }

        @Override
        public void onMouseDrag(MouseEvent event) {
            if (!panelBackground.isMousePressed() && draggableProperty().get() && getSelected() == null) {
                UIPanel.this.getStyle().x.set(event.getX() - xStart);
                UIPanel.this.getStyle().y.set(event.getY() - yStart);
            }
        }
    }
}

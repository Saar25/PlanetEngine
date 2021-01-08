package org.saar.gui;

import org.saar.gui.event.MouseEvent;

import java.util.List;

public final class EventsHandler {

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    protected void fireMousePressEvent(List<GuiController> components, MouseEvent event) {
        boolean found = false;
        for (GuiController component : components) {
            if (component.isMouseHover()) {
                component.onMousePressImpl(event);
                component.onMousePress(event);
                component.setSelected(!found);
                found = true;
            } else {
                component.setSelected(false);
            }
        }
    }

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseReleaseEvent(List<GuiController> components, MouseEvent event) {
        components.forEach(component -> {
            if (component.isMousePressed()) {
                component.onMouseReleaseImpl(event);
                component.onMouseRelease(event);
            }
        });
    }

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    protected void fireMouseEnterEvent(List<GuiController> components, MouseEvent event) {
        components.forEach(component -> {
            if (!component.isMouseHover() && component.inTouch(event.getX(), event.getY())) {
                component.onMouseEnterImpl(event);
                component.onMouseEnter(event);
            }
        });
    }

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    protected void fireMouseExitEvent(List<GuiController> components, MouseEvent event) {
        components.forEach(component -> {
            if (component.isMouseHover() &&
                    !component.inTouch(event.getX(), event.getY())) {
                component.onMouseExitImpl(event);
                component.onMouseExit(event);
            }
        });
    }

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseMoveEvent(List<GuiController> components, MouseEvent event) {
        components.forEach(component -> {
            if (component.isMouseHover()) {
                component.onMouseMoveImpl(event);
                component.onMouseMove(event);
            }
        });
    }

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseDragEvent(List<GuiController> components, MouseEvent event) {
        components.forEach(component -> {
            if (component.isMousePressed() && event.getButton() != null) {
                component.onMouseDragImpl(event);
                component.onMouseDrag(event);
            }
        });
    }

}

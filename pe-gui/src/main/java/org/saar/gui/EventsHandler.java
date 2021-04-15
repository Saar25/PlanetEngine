package org.saar.gui;

import org.saar.gui.event.MouseEvent;

import java.util.List;

public final class EventsHandler {

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    protected void fireMousePressEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (component.isMouseHover()) {
                component.onMousePress(event);
            }
        }
    }

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseReleaseEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (component.isMousePressed()) {
                component.onMouseRelease(event);
            }
        }
    }

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    protected void fireMouseEnterEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (!component.isMouseHover() && component.checkMouseInside(event.getX(), event.getY())) {
                component.onMouseEnter(event);
            }
        }
    }

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    protected void fireMouseExitEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (component.isMouseHover() &&
                    !component.checkMouseInside(event.getX(), event.getY())) {
                component.onMouseExit(event);
            }
        }
    }

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseMoveEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (component.isMouseHover()) {
                component.onMouseMove(event);
            }
        }
    }

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    protected void fireMouseDragEvent(List<UIComponent> components, MouseEvent event) {
        for (UIComponent component : components) {
            if (component.isMousePressed() && event.getButton() != null) {
                component.onMouseDrag(event);
            }
        }
    }

}

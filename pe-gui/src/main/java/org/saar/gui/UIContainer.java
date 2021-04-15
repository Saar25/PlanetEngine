package org.saar.gui;

import org.saar.gui.event.MouseEvent;
import org.saar.lwjgl.glfw.input.mouse.ClickEvent;
import org.saar.lwjgl.glfw.input.mouse.MouseButton;
import org.saar.lwjgl.glfw.input.mouse.MoveEvent;
import org.saar.lwjgl.glfw.input.mouse.ScrollEvent;
import org.saar.utils.list.ObservableList;

public class UIContainer extends UIParent {

    private final ObservableList<UIController> children = ObservableList.observableArrayList();

    public UIContainer() {

    }

    public void onMouseMoveEvent(MoveEvent moveEvent) {
        final MouseEvent event = MouseEvent.create(moveEvent);
        invokeMouseEnter(event);
        invokeMouseExit(event);
        if (moveEvent.getMouse().isButtonDown(MouseButton.PRIMARY)) {
            invokeMouseDrag(event);
        } else {
            invokeMouseMove(event);
        }
    }

    public void onMouseClickEvent(ClickEvent clickEvent) {
        final MouseEvent event = MouseEvent.create(clickEvent);
        if (clickEvent.isDown()) {
            invokeMousePress(event);
        } else {
            invokeMouseRelease(event);
        }
    }

    public void onMouseScrollEvent(ScrollEvent event) {

    }

    @Override
    public void process() {
        /*UIController selected = null;
        for (UIController child : getChildren()) {
            if (child.isSelected()) {
                selected = child;
            } else {
                child.process();
            }
        }
        if (selected != null) {
            selected.process();
        }*/
    }

    @Override
    public ObservableList<UIController> getChildren() {
        return children;
    }

    public UIController getSelected() {
        for (UIController child : getChildren()) {
            if (child.isSelected()) {
                return child;
            }
        }
        return null;
    }

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    protected final void invokeMousePress(MouseEvent event) {
        boolean found = false;
        for (int i = 0; i < getChildren().size(); i++) {
            final UIController component = getChildren().get(i);
            if (component.isMouseHover()) {
                component.onMousePressImpl(event);
                component.onMousePress(event);
                component.setSelected(!found);
                found |= component.isSelected();
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
    protected final void invokeMouseRelease(MouseEvent event) {
        getChildren().forEach(component -> {
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
    protected final void invokeMouseEnter(MouseEvent event) {
        for (UIController component : getChildren()) {
            if (!component.isMouseHover() && component.inTouch(event.getX(), event.getY())) {
                component.onMouseEnterImpl(event);
                component.onMouseEnter(event);
            }
        }
    }

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    protected final void invokeMouseExit(MouseEvent event) {
        for (UIController component : getChildren()) {
            if (component.isMouseHover() && !component.inTouch(event.getX(), event.getY())) {
                component.onMouseExitImpl(event);
                component.onMouseExit(event);
            }
        }
    }

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    protected final void invokeMouseMove(MouseEvent event) {
        for (UIController component : getChildren()) {
            if (component.isMouseHover()) {
                component.onMouseMoveImpl(event);
                component.onMouseMove(event);
            }
        }
    }

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    protected final void invokeMouseDrag(MouseEvent event) {
        for (UIController component : getChildren()) {
            if (component.isMousePressed() && event.getButton() != null) {
                component.onMouseDragImpl(event);
                component.onMouseDrag(event);
            }
        }
    }

    public void attachChild(UIController child) {
        getChildren().add(child);
    }
}

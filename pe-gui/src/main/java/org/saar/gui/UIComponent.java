package org.saar.gui;

import org.saar.gui.event.MouseEvent;
import org.saar.gui.style.Style;
import org.saar.lwjgl.glfw.input.mouse.ClickEvent;
import org.saar.lwjgl.glfw.input.mouse.MoveEvent;
import org.saar.lwjgl.glfw.input.mouse.ScrollEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a ui component, with out any logic
 * use this class to group UIObjects and render them as one
 */
public abstract class UIComponent {

    private final Style style = new Style();

    private final List<UIObject> uiObjects = new ArrayList<>();

    private boolean mouseHover;
    private boolean mousePressed;

    protected void add(UIObject uiObject) {
        this.uiObjects.add(uiObject);
    }

    public List<UIObject> getUiObjects() {
        return this.uiObjects;
    }

    public Style getStyle() {
        return this.style;
    }

    public final void onMouseMoveEvent(MoveEvent event) {
        final MouseEvent e = MouseEvent.create(event);
        final int x = event.getMouse().getXPos();
        final int y = event.getMouse().getYPos();

        final boolean mouseInside = checkMouseInside(x, y);

        if (mouseInside && !isMouseHover()) {
            mouseEnter(e);
        } else if (!mouseInside && isMouseHover()) {
            mouseExit(e);
        }

        if (mouseInside && !e.getButton().isPrimary()) {
            mouseMove(e);
        } else if (e.getButton().isPrimary() && isMousePressed()) {
            mouseDrag(e);
        }
    }

    public final void onMouseClickEvent(ClickEvent event) {
        final MouseEvent e = MouseEvent.create(event);
        final int x = event.getMouse().getXPos();
        final int y = event.getMouse().getYPos();

        if (event.isDown() && checkMouseInside(x, y)) {
            mousePress(e);
        } else if (isMousePressed()) {
            mouseRelease(e);
        }
    }

    public final void onMouseScrollEvent(ScrollEvent event) {
        /*final int x = event.getMouse().getXPos();
        final int y = event.getMouse().getYPos();

        if (checkMouseInside(x, y)) {
            final MouseEvent e = MouseEvent.create(event);
            onMouseScroll(e);
        }*/
    }


    private void mousePress(MouseEvent event) {
        this.mousePressed = true;
        onMousePress(event);
    }

    private void mouseRelease(MouseEvent event) {
        this.mousePressed = false;
        onMouseRelease(event);
    }

    private void mouseEnter(MouseEvent event) {
        this.mouseHover = true;
        onMouseEnter(event);
    }

    private void mouseExit(MouseEvent event) {
        this.mouseHover = false;
        onMouseExit(event);
    }

    private void mouseMove(MouseEvent event) {
        onMouseMove(event);
    }

    private void mouseDrag(MouseEvent event) {
        onMouseDrag(event);
    }

    /**
     * Returns whether the  the component contains the given point
     *
     * @param x the mouse x coordinate
     * @param y the mouse y coordinate
     * @return true if the point is in touch, false otherwise
     */
    public boolean checkMouseInside(int x, int y) {
        return getUiObjects().stream().anyMatch(o -> o.inTouch(x, y));
    }

    /**
     * Returns whether the ui component is currently selected
     *
     * @return true if selected, false otherwise
     */
    public final boolean isSelected() {
        return false;
    }

    /**
     * Returns whether the ui component is currently pressed by the mouse
     *
     * @return true if the object is pressed, false otherwise
     */
    public final boolean isMousePressed() {
        return this.mousePressed;
    }

    /**
     * Returns whether the mouse hovers the ui component
     *
     * @return true if the mouse is hover, false otherwise
     */
    public final boolean isMouseHover() {
        return this.mouseHover;
    }

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    public void onMousePress(MouseEvent event) {}

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    public void onMouseRelease(MouseEvent event) {}

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    public void onMouseEnter(MouseEvent event) {}

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    public void onMouseExit(MouseEvent event) {}

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    public void onMouseMove(MouseEvent event) {}

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    public void onMouseDrag(MouseEvent event) {}
}
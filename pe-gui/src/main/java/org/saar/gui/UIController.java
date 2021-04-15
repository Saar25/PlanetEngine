package org.saar.gui;

import org.saar.gui.event.MouseEvent;
import org.saar.gui.style.Style;
import org.saar.gui.style.Styleable;
import org.saar.lwjgl.glfw.input.mouse.ClickEvent;
import org.saar.lwjgl.glfw.input.mouse.MoveEvent;
import org.saar.lwjgl.glfw.input.mouse.ScrollEvent;
import org.saar.utils.list.ObservableList;

import java.util.List;

public abstract class UIController implements Styleable {

    private final Style style = new Style();
    private final ObservableList<UIObject> children;

    private boolean selectable = true;
    private boolean selected;
    private boolean mouseHover;
    private boolean mousePressed;

    protected UIController() {
        this.children = ObservableList.observableArrayList();
    }

    public final void onMouseMoveEvent(MoveEvent event) {
        final MouseEvent e = MouseEvent.create(event);
        final boolean inTouch = inTouch(event.getMouse().getXPos(), event.getMouse().getYPos());

        if (inTouch && !isMouseHover()) {
            onMouseEnterImpl(e);
            onMouseEnter(e);
        } else if (!inTouch && isMouseHover()) {
            onMouseExitImpl(e);
            onMouseExit(e);
        }

        if (inTouch && !e.getButton().isPrimary()) {
            onMouseMoveImpl(e);
            onMouseMove(e);
        } else if (e.getButton().isPrimary() && isMousePressed()) {
            onMouseDragImpl(e);
            onMouseDrag(e);
        }

    }

    public final void onMouseClickEvent(ClickEvent event) {
        final MouseEvent e = MouseEvent.create(event);
        if (event.isDown() && inTouch(event.getMouse().getXPos(), event.getMouse().getYPos())) {
            onMousePressImpl(e);
            onMousePress(e);
        } else if (isMousePressed()) {
            onMouseReleaseImpl(e);
            onMouseRelease(e);
        }
    }

    public final void onMouseScrollEvent(ScrollEvent event) {
        /*if (inTouch(event.getMouse().getXPos(), event.getMouse().getYPos())) {
            final MouseEvent e = MouseEvent.create(event);
            onMouseScroll(e);
        }*/
    }

    /**
     * Returns the style of the component, this style is the parent
     * of all the  styles of the component's children
     *
     * @return the style of the component
     */
    @Override
    public Style getStyle() {
        return style;
    }

    /**
     * Returns whether the  the component contains the given point
     *
     * @param x the x value
     * @param y the y value
     * @return true if the point is in touch, false otherwise
     */
    public boolean inTouch(int x, int y) {
        return getChildren().stream().anyMatch(o -> o.inTouch(x, y));
    }

    public void delete() {
        getChildren().forEach(UIObject::delete);
    }

    protected List<UIObject> getChildren() {
        return children;
    }

    /**
     * Returns whether the gui component is currently pressed by the mouse
     *
     * @return true if the object is pressed, false otherwise
     */
    public final boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Returns whether the mouse hovers the gui component
     *
     * @return true if the mouse is hover, false otherwise
     */
    public final boolean isMouseHover() {
        return mouseHover;
    }

    /**
     * Returns whether the gui component is currently selected
     *
     * @return true if selected, false otherwise
     */
    public final boolean isSelected() {
        return selected && selectable;
    }

    public final void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected final void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    /**
     * Invoked when a mouse button has been pressed on a component
     *
     * @param event the mouse event
     */
    public void onMousePress(MouseEvent event) {

    }

    final void onMousePressImpl(MouseEvent event) {
        mousePressed = true;
    }

    /**
     * Invoked when a mouse button has been released on the component
     *
     * @param event the mouse event
     */
    public void onMouseRelease(MouseEvent event) {

    }

    final void onMouseReleaseImpl(MouseEvent event) {
        mousePressed = false;
    }

    /**
     * Invoked when the mouse enters the component
     *
     * @param event the mouse event
     */
    public void onMouseEnter(MouseEvent event) {

    }

    final void onMouseEnterImpl(MouseEvent event) {
        mouseHover = true;
    }

    /**
     * Invoked when the mouse exits the component
     *
     * @param event the mouse event
     */
    public void onMouseExit(MouseEvent event) {

    }

    final void onMouseExitImpl(MouseEvent event) {
        mouseHover = false;
    }

    /**
     * Invoked when the mouse move on the component
     *
     * @param event the mouse event
     */
    public void onMouseMove(MouseEvent event) {

    }

    final void onMouseMoveImpl(MouseEvent event) {

    }

    /**
     * Invoked when the mouse drags on the component
     *
     * @param event the mouse event
     */
    public void onMouseDrag(MouseEvent event) {

    }

    final void onMouseDragImpl(MouseEvent event) {

    }
}

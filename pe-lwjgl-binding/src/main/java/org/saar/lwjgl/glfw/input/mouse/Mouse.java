package org.saar.lwjgl.glfw.input.mouse;

import org.lwjgl.glfw.GLFW;
import org.saar.lwjgl.glfw.event.EventListener;
import org.saar.lwjgl.glfw.event.EventListenersHelper;
import org.saar.lwjgl.glfw.event.IntValueChange;
import org.saar.lwjgl.glfw.event.OnAction;

public class Mouse {

    private final long window;

    private EventListenersHelper<ScrollEvent> helperScroll = EventListenersHelper.empty();
    private EventListenersHelper<ClickEvent> helperClick = EventListenersHelper.empty();
    private EventListenersHelper<MoveEvent> helperMove = EventListenersHelper.empty();

    private MouseCursor cursor = MouseCursor.NORMAL;

    private int x;
    private int y;
    private double scroll;

    public Mouse(long window) {
        this.window = window;
        init();
    }

    public void init() {
        GLFW.glfwSetMouseButtonCallback(this.window, (window, button, action, mods) -> {
            final boolean isDown = action == GLFW.GLFW_PRESS;
            final MouseButton mouseButton = MouseButton.valueOf(button);
            final ClickEvent event = new ClickEvent(this, mouseButton, isDown);
            this.helperClick.fireEvent(event);
        });
        GLFW.glfwSetCursorPosCallback(this.window, (window, xPos, yPos) -> {
            final MoveEvent event = new MoveEvent(this,
                    new IntValueChange(this.x, (int) xPos),
                    new IntValueChange(this.y, (int) yPos));

            this.x = (int) xPos;
            this.y = (int) yPos;

            this.helperMove.fireEvent(event);
        });
        GLFW.glfwSetScrollCallback(this.window, (window, xOffset, yOffset) -> {
            final ScrollEvent event = new ScrollEvent(this, yOffset);
            this.helperScroll.fireEvent(event);
        });
    }

    public void show() {
        setCursor(MouseCursor.NORMAL);
    }

    public void hide() {
        setCursor(MouseCursor.DISABLED);
    }

    public MouseCursor getCursor() {
        return this.cursor;
    }

    public void setCursor(MouseCursor cursor) {
        if (this.cursor != cursor) {
            GLFW.glfwSetInputMode(this.window,
                    GLFW.GLFW_CURSOR, cursor.get());
            this.cursor = cursor;
        }
    }

    public int getXPos() {
        return this.x;
    }

    public int getYPos() {
        return this.y;
    }

    public boolean isButtonDown(MouseButton button) {
        return isState(button, MouseButtonState.PRESS);
    }

    public boolean isState(MouseButton button, MouseButtonState buttonState) {
        final int state = getState(button);
        return state == buttonState.get();
    }

    public MouseButtonState getButtonState(MouseButton button) {
        final int state = getState(button);
        return MouseButtonState.valueOf(state);
    }

    public int getState(MouseButton button) {
        return GLFW.glfwGetMouseButton(this.window, button.get());
    }

    public void addScrollListener(EventListener<ScrollEvent> listener) {
        this.helperScroll = this.helperScroll.addListener(listener);
    }

    public void removeScrollListener(EventListener<ScrollEvent> listener) {
        this.helperScroll = this.helperScroll.removeListener(listener);
    }

    public void addClickListener(EventListener<ClickEvent> listener) {
        this.helperClick = this.helperClick.addListener(listener);
    }

    public void removeClickListener(EventListener<ClickEvent> listener) {
        this.helperClick = this.helperClick.removeListener(listener);
    }

    public void addMoveListener(EventListener<MoveEvent> listener) {
        this.helperMove = this.helperMove.addListener(listener);
    }

    public void removeMoveListener(EventListener<MoveEvent> listener) {
        this.helperMove = this.helperMove.removeListener(listener);
    }

    public OnAction<ClickEvent> onClick(MouseButton button) {
        return listener -> addClickListener(e -> {
            if (e.getButton() == button) {
                listener.onEvent(e);
            }
        });
    }
}

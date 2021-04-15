package org.saar.gui;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Model;
import org.saar.core.mesh.common.QuadMesh;
import org.saar.gui.position.Positioner;
import org.saar.gui.style.Style;
import org.saar.gui.style.property.Radiuses;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.objects.Rectangle;

/**
 * This class represent an object inside a UIComponent.
 * Every UIComponent is composed by at least one UIObject which
 * will be rendered by the renderer.
 *
 * @author Saar ----
 * @version 1.2
 * @since 18.2.2018
 */
public class UIBlock implements Model, UIChildElement {

    private final Style style;
    private final Positioner positioner;

    private final Texture2D texture;

    private UIElement parent;

    public UIBlock(UIComponent uiComponent) {
        this.texture = null;
        this.parent = uiComponent;
        this.positioner = new Positioner(this);
        this.style = new Style(this.positioner);
    }

    public UIBlock(UIComponent uiComponent, Texture2D texture) {
        this.texture = texture;
        this.parent = uiComponent;
        this.positioner = new Positioner(this);
        this.style = new Style(this.positioner);
    }

    public boolean inTouch(float mx, float my) {
        final Radiuses radiuses = this.style.getRadiuses();

        if (radiuses.isZero()) {
            return this.positioner.getBounds().contains(mx, my);
        }

        final float x = this.positioner.getX().get();
        final float y = this.positioner.getY().get();
        final float w = this.positioner.getWidth().get();
        final float h = this.positioner.getHeight().get();

        final float radius = radiuses.get(
                mx > x + w / 2, my < y + h / 2);

        final Rectangle bordersV = new Rectangle(
                x + radius, y, w - radius * 2, h);

        final Rectangle bordersH = new Rectangle(
                x, y + radius, w, h - radius * 2);

        if (bordersV.contains(mx, my) || bordersH.contains(mx, my)) {
            return true;
        }

        final float cx = mx < x + w / 2
                ? x + radius : x + w - radius;

        final float cy = my < y + h / 2
                ? y + radius : y + h - radius;

        final float dx = (cx - mx);
        final float dy = (cy - my);
        return radius * radius > dx * dx + dy * dy;
    }

    public Texture2D getTexture() {
        return this.texture;
    }

    public Style getStyle() {
        return this.style;
    }

    @Override
    public Positioner getPositioner() {
        return this.positioner;
    }

    @Override
    public UIElement getParent() {
        return this.parent;
    }

    public void setParent(UIElement parent) {
        this.parent = parent;
    }

    @Override
    public void delete() {
        if (this.texture != null) {
            this.texture.delete();
        }
    }

    @Override
    public void draw() {
        getMesh().draw();
    }

    @Override
    public Mesh getMesh() {
        return QuadMesh.INSTANCE;
    }
}

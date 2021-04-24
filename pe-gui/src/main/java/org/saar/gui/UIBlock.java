package org.saar.gui;

import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Model;
import org.saar.core.mesh.common.QuadMesh;
import org.saar.gui.style.Style;
import org.saar.gui.style.redius.StyleRadiuses;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
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

    private final Style style = new Style(this);

    private ReadOnlyTexture texture = null;
    private ReadOnlyTexture discardMap = null;

    private UIElement parent;

    public boolean inTouch(float mx, float my) {
        final StyleRadiuses radiuses = this.style.getRadiuses();

        if (radiuses.isZero()) {
            return this.style.getBounds().contains(mx, my);
        }

        final float x = this.style.getX().get();
        final float y = this.style.getY().get();
        final float w = this.style.getWidth().get();
        final float h = this.style.getHeight().get();

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

    public ReadOnlyTexture getTexture() {
        return this.texture;
    }

    public void setTexture(ReadOnlyTexture texture) {
        this.texture = texture;
    }

    public ReadOnlyTexture getDiscardMap() {
        return this.discardMap;
    }

    public void setDiscardMap(ReadOnlyTexture discardMap) {
        this.discardMap = discardMap;
    }

    @Override
    public Style getStyle() {
        return this.style;
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
        if (this.discardMap != null) {
            this.discardMap.delete();
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

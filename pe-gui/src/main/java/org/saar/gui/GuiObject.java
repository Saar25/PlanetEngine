package org.saar.gui;

import org.saar.gui.style.Style;
import org.saar.gui.style.Styleable;
import org.saar.gui.style.property.Radiuses;
import org.saar.lwjgl.opengl.textures.Texture2D;
import org.saar.maths.objects.Rectangle;

/**
 * This class represent an object inside a GuiComponent.
 * Every GuiComponent is composed by at least one GuiObject which
 * will be rendered by the renderer.
 *
 * @author Saar ----
 * @version 1.2
 * @since 18.2.2018
 */
public class GuiObject implements Styleable {

    private final Style style = new Style();

    private final Texture2D texture;

    public GuiObject() {
        this.texture = null;
//        this.texture = new Texture2D(Texture.NULL);
    }

    public GuiObject(Texture2D texture) {
        this.texture = texture;
    }

    public boolean inTouch(float mx, float my) {
        final Radiuses radiuses = this.style.radiuses;

        if (radiuses.isZero()) {
            return this.style.bounds.contains(mx, my);
        }

        final float x = this.style.position.x.get();
        final float y = this.style.position.y.get();
        final float w = this.style.dimensions.width.get();
        final float h = this.style.dimensions.height.get();

        final float radius = radiuses.get(
                mx > x + w / 2, my < y + h / 2).get();

        final Rectangle bordersV = new Rectangle(
                x + radius, y, w - radius * 2, h);

        final Rectangle bordersH = new Rectangle(
                x, y + radius, w, h - radius * 2);

        if (bordersV.contains(mx, my) || bordersH.contains(mx, my)) {
            return true;
        }

        float cx = this.style.bounds.xCenter();
        float cy = this.style.bounds.yCenter();
        cx = mx < cx ? x + radius : x + w - radius;
        cy = my < cy ? y + radius : y + h - radius;

        final float dx = (cx - mx);
        final float dy = (cy - my);
        return radius * radius > dx * dx + dy * dy;
    }

    public Texture2D getTexture() {
        return this.texture;
    }

    @Override
    public Style getStyle() {
        return this.style;
    }

    public void delete() {
        if (this.texture != null) {
            this.texture.delete();
        }
    }
}

package org.saar.gui.objects;

import org.saar.gui.UIBlock;
import org.saar.gui.graphics.BufferedGraphics;
import org.saar.gui.graphics.Graphics;
import org.saar.gui.style.property.Colours;
import org.saar.gui.style.colour.ReadonlyColour;
import org.saar.lwjgl.opengl.textures.Texture2D;

public abstract class TGraphical extends UIBlock {

    private final Graphics graphics;

    private ReadonlyColour background = null;

    public TGraphical(int width, int height) {
        super(new Texture2D(width, height));
        this.graphics = new BufferedGraphics(getTexture());

        getPositioner().getWidth().set(width);
        getPositioner().getHeight().set(height);
    }

    public void setBackground(ReadonlyColour background) {
        this.background = background;
    }

    public void process() {
        graphics.clear(background);
        graphics.setColour(Colours.BLACK);
        paint(graphics);
        graphics.process();
    }

    public abstract void paint(Graphics g);

    @Override
    public void delete() {
        super.delete();
        graphics.delete();
    }
}

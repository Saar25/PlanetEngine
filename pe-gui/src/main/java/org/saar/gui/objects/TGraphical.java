package org.saar.gui.objects;

import org.saar.gui.UIBlock;
import org.saar.gui.graphics.BufferedGraphics;
import org.saar.gui.graphics.Graphics;
import org.saar.gui.style.Colour;
import org.saar.gui.style.Colours;
import org.saar.lwjgl.opengl.textures.Texture2D;

public abstract class TGraphical extends UIBlock {

    private final Graphics graphics;

    private Colour background = null;

    public TGraphical(int width, int height) {
        super(new Texture2D(width, height));
        this.graphics = new BufferedGraphics(getTexture());

        getStyle().getWidth().set(width);
        getStyle().getHeight().set(height);
    }

    public void setBackground(Colour background) {
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

package org.saar.gui.objects;

import org.saar.gui.graphics.Graphics;
import org.saar.gui.style.property.Colour;
import org.saar.gui.style.property.Colours;

public class TSquare extends TGraphical {

    public TSquare(int width, int height) {
        super(width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColour(new Colour(25, 25, 25, 127));
        g.fillRectangle(100, 100, 1000, 600);
        g.setColour(Colours.PURPLE);
        g.drawRectangle(10, 10, 10, 10);
        g.setColour(Colours.BLUE);
        g.fillOval(50, 50, 20, 30);
        g.setColour(Colours.RED);
        g.fillRectangle(50, 200, 100, 100);
    }
}

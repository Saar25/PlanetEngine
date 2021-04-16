package org.saar.gui.graphics;

import org.saar.gui.style.colour.ReadonlyColour;
import org.saar.maths.objects.Polygon;

public interface Graphics {

    void setColour(ReadonlyColour colour);

    void drawLine(int x1, int y1, int x2, int y2);

    void drawRectangle(int x, int y, int w, int h);

    void fillRectangle(int x, int y, int w, int h);

    void drawOval(int cx, int cy, int a, int b);

    void fillOval(int cx, int cy, int a, int b);

    void fillPolygon(Polygon polygon);

    void clear(ReadonlyColour clearColour);

    void process();

    void delete();

}

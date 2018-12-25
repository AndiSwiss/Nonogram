package Draw;

import Data.InitialData;
import Data.Zone;
import NonogramStructure.Box;
import NonogramStructure.Nonogram;
import processing.core.PApplet;

public class DrawBasicObjects {
    private InitialData id;
    private PApplet p;
    private Nonogram no;

    public DrawBasicObjects(PApplet p, Nonogram no) {
        id = new InitialData();
        this.p = p;
        this.no = no;
    }

    /**
     * Draws a box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param box Box
     */
    void drawBox(Box box) {
        int x = box.getPosX();
        int y = box.getPosY();

        int color;
        switch (box.getState()) {
            case UNKNOWN:
                color = id.cLightGrey2;
                break;
            case WHITE:
                color = id.cWhite;
                break;
            case BLACK:
                color = id.cBlack;
                break;
            default:
                throw new IllegalArgumentException("Undefined state in method drawBox! Color: " + box.getState());
        }

        drawRectangle(Zone.MAIN, x, y, 1, 1, color, 1, id.cBackgroundLine);


        // redraw thicker horizontal and vertical lines every five lines:
        // horizontal (on left side of the box):
        if (x % 5 == 0) {
            drawLine(Zone.MAIN, x, y, false, 1, 3, id.cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (x == no.getHorizontalBoxesCount() - 1) {
            drawLine(Zone.MAIN, x + 1, y, false, 1, 3, id.cBackgroundLine);
        }

        // vertical (on top side of the box):
        if (y % 5 == 0) {
            drawLine(Zone.MAIN, x, y, true, 1, 3, id.cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (y == no.getVerticalBoxesCount() - 1) {
            drawLine(Zone.MAIN, x, y + 1, true, 1, 3, id.cBackgroundLine);
        }
    }


    /**
     * Draws a rectangle in a specific zone.
     *
     * @param zone         zone
     * @param x            x relative to zone (is automatically multiplied by boxSize)
     * @param y            y relative to zone (is automatically multiplied by boxSize)
     * @param sizeX        horizontal size
     * @param sizeY        vertical size
     * @param fillColor    color for the fill
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
    void drawRectangle(Zone zone, int x, int y, int sizeX, int sizeY, int fillColor, int strokeWeight, int strokeColor) {
        p.fill(fillColor);
        p.strokeWeight(strokeWeight);
        p.stroke(strokeColor);
        p.rect(zone.getMinX() + x * no.getBoxSize(),
                zone.getMinY() + y * no.getBoxSize(),
                no.getBoxSize() * sizeX,
                no.getBoxSize() * sizeY);
    }


    /**
     * Draw a horizontal or a vertical line inside a specific zone.
     *
     * @param zone         zone
     * @param x            x relative to zone (is automatically multiplied by boxSize)
     * @param y            y relative to zone (is automatically multiplied by boxSize)
     * @param horizontal   true for horizontal, false for vertical
     * @param length       length
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
    void drawLine(Zone zone, double x, double y, boolean horizontal, int length, int strokeWeight, int strokeColor) {
        p.strokeWeight(strokeWeight);
        p.stroke(strokeColor);

        double x2;
        double y2;

        if (horizontal) {
            x2 = zone.getMinX() + no.getBoxSize() * (x + length);
            y2 = zone.getMinY() + no.getBoxSize() * y;

        } else {
            x2 = zone.getMinX() + no.getBoxSize() * x;
            y2 = zone.getMinY() + no.getBoxSize() * (y + length);

        }

        p.line(zone.getMinX() + (int) (no.getBoxSize() * x),
                zone.getMinY() + (int) (no.getBoxSize() * y),
                (int) x2, (int) y2)
        ;
    }


    //------//
    // Text //
    //------//
    /**
     * Overloaded method
     */
    public void drawText(String string, Zone zone, double boxX, double boxY) {
        drawText(string, zone, boxX, boxY, 1);
    }


    /**
     * Overloaded method
     */
    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize) {
        drawText(string, zone, boxX, boxY, relativeSize, id.cBlack);
    }


    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize, int color) {
        p.fill(color);

        int x = zone.getMinX() + (int) (boxX * no.getBoxSize());
        int y = zone.getMinY() + (int) ((boxY + 1) * no.getBoxSize());

        p.textSize((int) (relativeSize * no.getBoxSize()));
        p.text(string, x, y);
    }


}

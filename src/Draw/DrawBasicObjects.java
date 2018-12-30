package Draw;

import Data.Align;
import Data.InitialData;
import Data.Zone;
import NonogramStructure.Box;
import NonogramStructure.Nonogram;
import processing.core.PApplet;

public class DrawBasicObjects {
    private final InitialData id;
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
     * @param boxX            boxX relative to zone (is automatically multiplied by boxSize)
     * @param boxY            boxY relative to zone (is automatically multiplied by boxSize)
     * @param boxSizeX        horizontal size
     * @param boxSizeY        vertical size
     * @param fillColor    color for the fill
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
    void drawRectangle(Zone zone, int boxX, int boxY, int boxSizeX, int boxSizeY, int fillColor, int strokeWeight, int strokeColor) {
        p.fill(fillColor);
        p.strokeWeight(strokeWeight);
        p.stroke(strokeColor);
        p.rect(zone.getMinX() + boxX * no.getBoxSize(),
                zone.getMinY() + boxY * no.getBoxSize(),
                no.getBoxSize() * boxSizeX,
                no.getBoxSize() * boxSizeY);
    }


    /**
     * Draw a horizontal or a vertical line inside a specific zone.
     *
     * @param zone         zone
     * @param boxX            boxX relative to zone (is automatically multiplied by boxSize)
     * @param boxY            boxY relative to zone (is automatically multiplied by boxSize)
     * @param horizontal   true for horizontal, false for vertical
     * @param boxLength       boxLength
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
    void drawLine(Zone zone, double boxX, double boxY, boolean horizontal, int boxLength, int strokeWeight, int strokeColor) {
        p.strokeWeight(strokeWeight);
        p.stroke(strokeColor);

        double x2;
        double y2;

        if (horizontal) {
            x2 = zone.getMinX() + no.getBoxSize() * (boxX + boxLength);
            y2 = zone.getMinY() + no.getBoxSize() * boxY;

        } else {
            x2 = zone.getMinX() + no.getBoxSize() * boxX;
            y2 = zone.getMinY() + no.getBoxSize() * (boxY + boxLength);

        }

        p.line(zone.getMinX() + (int) (no.getBoxSize() * boxX),
                zone.getMinY() + (int) (no.getBoxSize() * boxY),
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
        drawText(string, zone, boxX, boxY, 1, id.cBlack, Align.LEFT, false);
    }


    /**
     * Overloaded method
     */
    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize) {
        drawText(string, zone, boxX, boxY, relativeSize, id.cBlack, Align.LEFT, false);
    }

    /**
     * Overloaded method
     */
    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize, int color) {
        drawText(string, zone, boxX, boxY, relativeSize, color, Align.LEFT, false);
    }

    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize, int color, Align hAlign, boolean verticallyCentered) {
        // save the style for easy resetting later:
        p.pushStyle();
        // style settings

        int verticalAlignment = verticallyCentered ? p.CENTER : p.BASELINE;

        p.fill(color);
        switch (hAlign) {
            case LEFT:
                p.textAlign(p.LEFT, verticalAlignment);
                break;
            case RIGHT:
                p.textAlign(p.RIGHT, verticalAlignment);
                break;
            case CENTER:
                p.textAlign(p.CENTER, verticalAlignment);
        }

        int x = zone.getMinX() + (int) (boxX * no.getBoxSize());
        int y = zone.getMinY() + (int) ((boxY + 1) * no.getBoxSize());

        p.textSize((int) (relativeSize * no.getBoxSize()));
        p.text(string, x, y);

        // reset the style:
        p.popStyle();
    }


}

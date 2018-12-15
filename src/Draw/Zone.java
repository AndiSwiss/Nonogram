package Draw;


import processing.core.PApplet;

import static Data.DataStorage.*;
import static Data.InitialData.*;

/**
 * Check the Zones via the method drawAllZoneBoxesForTesting() in Nonogram.java
 */
public enum Zone {
    HEADER(1, 1,
            maxSideNumbers + horizontalBoxes + rightSideWidth, headerHeight,
            cBackground),
    MAIN(1 + maxSideNumbers, 1 + headerHeight + 1 + maxTopNumbers,
            horizontalBoxes, verticalBoxes,
            cLightGrey2),
    LEFT(1, 1 + headerHeight + 1 + maxTopNumbers,
            maxSideNumbers, verticalBoxes,
            cBackground),
    RIGHT(1 + maxSideNumbers + horizontalBoxes, 1 + headerHeight + 1 + maxTopNumbers,
            rightSideWidth, verticalBoxes,
            cBackground),
    TOP(1 + maxSideNumbers, 1 + headerHeight + 1,
            horizontalBoxes, maxTopNumbers,
            cBackground),
    BOTTOM(1, 1 + headerHeight + 1 + maxTopNumbers + verticalBoxes + 1,
            maxSideNumbers + horizontalBoxes + rightSideWidth, bottomHeight,
            cBackground),
    FOOTER(0, myHeight / boxSize - footerHeight,
            myWidth / boxSize, footerHeight,
            cLightGrey + 1);


    private String name;
    private int minX;
    private int minY;
    private int sizeX;
    private int sizeY;
    private int maxX;
    private int maxY;
    private int color;


    Zone(int minX, int minY, int sizeX, int sizeY, int color) {


        this.minX = minX * boxSize;
        this.minY = minY * boxSize;
        this.sizeX = sizeX * boxSize;
        this.sizeY = sizeY * boxSize;
        this.color = color;

        this.maxX = this.minX + this.sizeX;
        this.maxY = this.minY + this.sizeY;

        // keep the first letter capitalized, but make the rest lowercase:
        name = name().charAt(0) + name().substring(1).toLowerCase();
    }

    static void drawAllZoneBoxesForTesting(PApplet p) {
        p.stroke(cGrey);
        p.strokeWeight(1);
        for (Zone z : Zone.values()) {
            p.fill(z.getColor());
            p.rect(z.getMinX(), z.getMinY(), z.getSizeX(), z.getSizeY());
            p.fill(cBlack);
            p.text(z.getName(), z.getMinX() + boxSize, z.getMinY() + boxSize);
        }
    }

    public String getName() {
        return name;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    public String debugString() {
        return "Zone{" +
                "name='" + name + '\'' +
                ", minX=" + minX +
                ", minY=" + minY +
                ", sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                ", color=" + color +
                '}';
    }}

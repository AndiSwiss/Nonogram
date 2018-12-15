package Draw;


import processing.core.PApplet;

import static Data.DataStorage.*;
import static Data.InitialData.*;

/**
 * Check the Zones via the method drawAllZoneBoxesForTesting() in Nonogram.java
 */
public enum Zone {
    HEADER(),
    MAIN(),
    LEFT(),
    RIGHT(),
    TOP(),
    BOTTOM(),
    FOOTER();

    private String name;
    private int minX;
    private int minY;
    private int sizeX;
    private int sizeY;
    private int maxX;
    private int maxY;
    private int color;


    /**
     * the private constructor for zone:
     */
    Zone() {
        updateZone();
    }

    void updateZone() {
        switch (name()) {
            case "HEADER":
                minX = 1;
                minY = 1;
                sizeX = myWidth / boxSize - 2;
                sizeY = headerHeight;
                color = cBackground;
                break;
            case "MAIN":
                minX = 1 + maxSideNumbers;
                minY = 1 + headerHeight + 1 + maxTopNumbers;
                sizeX = horizontalBoxes;
                sizeY = verticalBoxes;
                color = cLightGrey2;
                break;
            case "LEFT":
                minX = 1;
                minY = 1 + headerHeight + 1 + maxTopNumbers;
                sizeX = maxSideNumbers;
                sizeY = verticalBoxes;
                color = cBackground;
                break;
            case "RIGHT":
                minX = 1 + maxSideNumbers + horizontalBoxes;
                minY = 1 + headerHeight + 1 + maxTopNumbers;
                sizeX = rightSideWidth;
                sizeY = verticalBoxes;
                color = cBackground;
                break;
            case "TOP":
                minX = 1 + maxSideNumbers;
                minY = 1 + headerHeight + 1;
                sizeX = horizontalBoxes;
                sizeY = maxTopNumbers;
                color = cBackground;
                break;
            case "BOTTOM":
                minX = 1;
                minY = 1 + headerHeight + 1 + maxTopNumbers + verticalBoxes + 1;
                sizeX = myWidth / boxSize - 2;
                sizeY = myHeight / boxSize - footerHeight - minY - 1;
                color = cBackground;
                break;
            case "FOOTER":
                minX = 0;
                minY = myHeight / boxSize - footerHeight;
                sizeX = myWidth / boxSize;
                sizeY = footerHeight;
                color = cLightGrey + 1;
                break;
                default:
                    throw new IllegalArgumentException("ERROR in updateZone() in enum Zone: undefined Zone: " + name());
        }

        minX *= boxSize;
        minY *= boxSize;
        sizeX *= boxSize;
        sizeY *= boxSize;

        maxX = minX + sizeX;
        maxY = minY + sizeY;

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

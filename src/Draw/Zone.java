package Draw;


import static Data.DataStorage.*;
import static Data.InitialData.*;

/**
 * Check the Zones via the method drawAllZoneBoxesForTesting() in Nonogram.java
 */
public enum Zone {
    HEADER(1, 1,
            maxSideNumbers + horizontalBoxes + rightSideWidth, headerHeight,
            cLightGrey),
    MAIN(1 + maxSideNumbers, 1 + headerHeight + 1 + maxTopNumbers,
            horizontalBoxes, verticalBoxes,
            cLightGrey),
    LEFT(1, 1 + headerHeight + 1 + maxTopNumbers,
            maxSideNumbers, verticalBoxes,
            cLightGrey),
    RIGHT(1 + maxSideNumbers + horizontalBoxes, 1 + headerHeight + 1 + maxTopNumbers,
            rightSideWidth, verticalBoxes,
            cLightGrey),
    TOP(1 + maxSideNumbers, 1 + headerHeight + 1,
            horizontalBoxes, maxTopNumbers,
            cLightGrey),
    BOTTOM(1, 1 + headerHeight + 1 + maxTopNumbers + verticalBoxes + 1,
            maxSideNumbers + horizontalBoxes + rightSideWidth, bottomHeight,
            cLightGrey),
    FOOTER(1, 1 + headerHeight + 1 + maxTopNumbers + verticalBoxes + bottomHeight,
            maxSideNumbers + horizontalBoxes + rightSideWidth, footerHeight,
            cLightGrey);


    private String name;
    private int minX;
    private int minY;
    private int sizeX;
    private int sizeY;
    private int maxX;
    private int maxY;
    private int backgroundColor;


    Zone(int minX, int minY, int sizeX, int sizeY, int backgroundColor) {


        this.minX = minX * boxSize;
        this.minY = minY * boxSize;
        this.sizeX = sizeX * boxSize;
        this.sizeY = sizeY * boxSize;
        this.backgroundColor = backgroundColor;

        this.maxX = minX + sizeX;
        this.maxY = minY + sizeY;

        // keep the first letter capitalized, but make the rest lowercase:
        name = name().charAt(0) + name().substring(1).toLowerCase();


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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public String toString() {
        return name;
    }
}

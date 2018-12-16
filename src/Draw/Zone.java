package Draw;


import NonogramStructure.Nonogram;
import Data.InitialData;
import processing.core.PApplet;

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


    void updateZone(InitialData id, Nonogram no) {
        switch (name()) {
            case "HEADER":
                minX = 1;
                minY = 1;
                sizeX = id.myWidth / no.boxSize - 2;
                sizeY = id.headerHeight;
                color = id.cBackground;
                break;
            case "MAIN":
                minX = 1 + no.getMaxSideNumbers();
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = no.getHorizontalBoxes();
                sizeY = no.getVerticalBoxes();
                color = id.cLightGrey2;
                break;
            case "LEFT":
                minX = 1;
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = no.getMaxSideNumbers();
                sizeY = no.getVerticalBoxes();
                color = id.cBackground;
                break;
            case "RIGHT":
                minX = 1 + no.getMaxSideNumbers() + no.getHorizontalBoxes();
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = id.rightSideWidth;
                sizeY = no.getVerticalBoxes();
                color = id.cBackground;
                break;
            case "TOP":
                minX = 1 + no.getMaxSideNumbers();
                minY = 1 + id.headerHeight + 1;
                sizeX = no.getHorizontalBoxes();
                sizeY = no.getMaxTopNumbers();
                color = id.cBackground;
                break;
            case "BOTTOM":
                minX = 1;
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers() + no.getVerticalBoxes() + 1;
                sizeX = id.myWidth / no.boxSize - 2;
                sizeY = id.myHeight / no.boxSize - id.footerHeight - minY;
                color = id.cBackground;
                break;
            case "FOOTER":
                minX = 0;
                minY = id.myHeight / no.boxSize - id.footerHeight;
                sizeX = id.myWidth / no.boxSize;
                sizeY = id.footerHeight;
                color = id.cLightGrey + 1;
                break;
                default:
                    throw new IllegalArgumentException("ERROR in updateZone() in enum Zone: undefined Zone: " + name());
        }

        minX *= no.boxSize;
        minY *= no.boxSize;
        sizeX *= no.boxSize;
        sizeY *= no.boxSize;

        maxX = minX + sizeX;
        maxY = minY + sizeY;

        // keep the first letter capitalized, but make the rest lowercase:
        name = name().charAt(0) + name().substring(1).toLowerCase();


    }

    static void drawAllZoneBoxesForTesting(PApplet p, InitialData id, Nonogram no) {
        p.stroke(id.cGrey);
        p.strokeWeight(1);
        for (Zone z : Zone.values()) {
            p.fill(z.getColor());
            p.rect(z.getMinX(), z.getMinY(), z.getSizeX(), z.getSizeY());
            p.fill(id.cBlack);
            p.text(z.getName(), z.getMinX() + no.boxSize, z.getMinY() + no.boxSize);
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

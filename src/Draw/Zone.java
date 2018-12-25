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
                sizeX = id.myWidth / no.getBoxSize() - 2;
                sizeY = id.headerHeight;
                color = id.cBackground;
                break;
            case "MAIN":
                minX = 1 + no.getMaxSideNumbers();
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = no.getHorizontalBoxesCount();
                sizeY = no.getVerticalBoxesCount();
                color = id.cLightGrey2;
                break;
            case "LEFT":
                minX = 1;
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = no.getMaxSideNumbers();
                sizeY = no.getVerticalBoxesCount();
                color = id.cBackground;
                break;
            case "RIGHT":
                minX = 1 + no.getMaxSideNumbers() + no.getHorizontalBoxesCount();
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers();
                sizeX = id.rightSideWidth;
                sizeY = no.getVerticalBoxesCount();
                color = id.cBackground;
                break;
            case "TOP":
                minX = 1 + no.getMaxSideNumbers();
                minY = 1 + id.headerHeight + 1;
                sizeX = no.getHorizontalBoxesCount();
                sizeY = no.getMaxTopNumbers();
                color = id.cBackground;
                break;
            case "BOTTOM":
                minX = 1;
                minY = 1 + id.headerHeight + 1 + no.getMaxTopNumbers() + no.getVerticalBoxesCount() + 1;
                sizeX = id.myWidth / no.getBoxSize() - 2;
                sizeY = id.myHeight / no.getBoxSize() - id.footerHeight - minY;
                color = id.cBackground;
                break;
            case "FOOTER":
                minX = 0;
                minY = id.myHeight / no.getBoxSize() - id.footerHeight;
                sizeX = id.myWidth / no.getBoxSize();
                sizeY = id.footerHeight;
                color = id.cLightGrey + 1;
                break;
                default:
                    throw new IllegalArgumentException("ERROR in updateZone() in enum Zone: undefined Zone: " + name());
        }

        minX *= no.getBoxSize();
        minY *= no.getBoxSize();
        sizeX *= no.getBoxSize();
        sizeY *= no.getBoxSize();

        maxX = minX + sizeX;
        maxY = minY + sizeY;

        // keep the first letter capitalized, but make the rest lowercase:
        name = name().charAt(0) + name().substring(1).toLowerCase();


    }

    public static void drawAllZoneBoxesForTesting(PApplet p, Nonogram no) {
        InitialData id = new InitialData();
        p.stroke(id.cGrey);
        p.strokeWeight(1);
        for (Zone z : Zone.values()) {
            p.fill(z.getColor());
            p.rect(z.getMinX(), z.getMinY(), z.getSizeX(), z.getSizeY());
            p.fill(id.cBlack);
            p.text(z.getName(), z.getMinX() + no.getBoxSize(), z.getMinY() + no.getBoxSize());
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

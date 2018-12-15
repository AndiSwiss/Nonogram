package Draw;

import java.util.Objects;

public class UiElement {
    private String name;
    private String message;
    private Zone zone;
    private int minX;
    private int minY;
    private int sizeX;
    private int sizeY;

    //calculated from sizeX:
    private int maxX;
    //calculated from sizeY:
    private int maxY;

    /**
     * @param name    name
     * @param message message, can be empty, if no message is wanted
     * @param zone    zone
     * @param minX    minX
     * @param minY    minY
     * @param sizeX   sizeX
     * @param sizeY   sizeY
     */
    public UiElement(String name, String message, Zone zone, int minX, int minY, int sizeX, int sizeY) {
        this.name = name;
        this.message = message;
        this.zone = zone;
        this.minX = minX;
        this.minY = minY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        maxX = minX + sizeX;
        maxY = minY + sizeY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UiElement uiElement = (UiElement) o;
        return minX == uiElement.minX &&
                minY == uiElement.minY &&
                sizeX == uiElement.sizeX &&
                sizeY == uiElement.sizeY &&
                Objects.equals(name, uiElement.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minX, minY, sizeX, sizeY);
    }


    //---------//
    // Getters //
    //---------//


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

    public String getMessage() {
        return message;
    }

    public Zone getZone() {
        return zone;
    }

    @Override
    public String toString() {
        return "UiElement{" +
                "name='" + name + '\'' +
                ", minX=" + minX +
                ", minY=" + minY +
                ", sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                '}';
    }
}

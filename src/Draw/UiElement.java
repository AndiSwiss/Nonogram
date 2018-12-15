package Draw;

import Data.Position;

import java.util.Objects;

public class UiElement {
    private String name;
    private String message;
    private Position start;
    private Zone zone;
    private int relSizeX;
    private int relSizeY;
    private Position end;
    private boolean selected;

    /**
     * @param name     name
     * @param message  message, can be empty, if no message is wanted
     * @param start    start position
     * @param relSizeX relSizeX
     * @param relSizeY relSizeY
     */
    public UiElement(String name, String message, Position start, int relSizeX, int relSizeY) {
        this.name = name;
        this.message = message;
        this.start = start;
        this.relSizeX = relSizeX;
        this.relSizeY = relSizeY;

        zone = start.getZone();

        end = new Position(start.getZone(),
                start.getRelX() + relSizeX,
                start.getRelY() + relSizeY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UiElement uiElement = (UiElement) o;
        return relSizeX == uiElement.relSizeX &&
                relSizeY == uiElement.relSizeY &&
                Objects.equals(name, uiElement.name) &&
                Objects.equals(start, uiElement.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start, relSizeX, relSizeY);
    }

    //---------//
    // Getters //
    //---------//


    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Position getStart() {
        return start;
    }

    public int getRelSizeX() {
        return relSizeX;
    }

    public int getRelSizeY() {
        return relSizeY;
    }

    public Position getEnd() {
        return end;
    }

    public Zone getZone() {
        return zone;
    }

    public int getRelStartX() {
        return start.getRelX();
    }

    public int getRelStartY() {
        return start.getRelY();
    }

    public int getAbsStartX() {
        return start.getAbsX();
    }

    public int getAbsStartY() {
        return start.getAbsY();
    }

    public int getAbsEndX() {
        return end.getAbsX();
    }

    public int getAbsEndY() {
        return end.getAbsY();
    }

    @Override
    public String toString() {
        return "UiElement{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", start=" + start +
                ", relSizeX=" + relSizeX +
                ", relSizeY=" + relSizeY +
                ", end=" + end +
                '}';
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

package UiElements;

import Data.Position;
import Draw.Zone;

import java.util.Objects;

public abstract class UiElement {
    private String name;
    private String message;
    private Zone zone;
    private Position start;
    private int relSizeX;
    private int relSizeY;
    private Position end;
    private boolean selected;

    /**
     * @param name     name
     * @param message  message, can be empty, if no message is wanted
     */
    UiElement(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UiElement uiElement = (UiElement) o;
        return Objects.equals(name, uiElement.name) &&
                Objects.equals(message, uiElement.message);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, message);
    }


    void updatePositionValues(Position start, int relSizeX, int relSizeY, int boxSize) {
        this.start = start;
        this.relSizeX = relSizeX;
        this.relSizeY = relSizeY;

        zone = start.getZone();

        end = new Position(start.getZone(),
                start.getRelX() + relSizeX,
                start.getRelY() + relSizeY, boxSize);
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

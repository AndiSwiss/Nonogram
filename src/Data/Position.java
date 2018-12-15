package Data;

import Draw.Zone;

import java.util.Objects;

import static Data.DataStorage.boxSize;

public class Position {
    private int absX;
    private int absY;
    private Zone zone;
    private int relX;
    private int relY;


    public Position(int absX, int absY) {
        this.absX = absX;
        this.absY = absY;
        convertAbsoluteToRelativePosition(absX, absY);
    }

    public Position(Zone zone, int relX, int relY) {
        this.zone = zone;
        this.relX = relX;
        this.relY = relY;
        convertRelativeToAbsolutePosition(zone, relX, relY);
    }

    public Position convertRelativeToAbsolutePosition(Zone zone, int relX, int relY) {
        absX = (relX * boxSize) + zone.getMinX();
        absY = (relY * boxSize) + zone.getMinY();

        return this;
    }

    public Zone checkInWhichZoneTheAbsolutePositionIs(int absX, int absY) {
        for (Zone z : Zone.values()) {
            if (absX >= z.getMinX() && absX <= z.getMaxX()
                    && absY >= z.getMinY() && absY <= z.getMaxY()) {
                return z;
            }
        }
        System.out.println("ERROR!!!! is not a a zone, absX and absY were: " + absX + "/" + absY);
        return null;
    }

    public Position convertAbsoluteToRelativePosition(int absX, int absY) {
        // check in which zone it is
        zone = checkInWhichZoneTheAbsolutePositionIs(absX, absY);

        // then calculate the relative x (including concerning the boxSize):
        if (zone != null) {
            relX = (absX - zone.getMinX()) / boxSize;
            relY = (absY - zone.getMinY()) / boxSize;

        } else {
            relX = -1;
            relY = -1;
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return absX == position.absX &&
                absY == position.absY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(absX, absY);
    }

    @Override
    public String toString() {
        return "Position{" +
                "absX=" + absX +
                ", absY=" + absY +
                ", zone=" + zone +
                ", relX=" + relX +
                ", relY=" + relY +
                '}';
    }

    public int getAbsX() {
        return absX;
    }

    public int getAbsY() {
        return absY;
    }

    public Zone getZone() {
        return zone;
    }

    public int getRelX() {
        return relX;
    }

    public int getRelY() {
        return relY;
    }
}

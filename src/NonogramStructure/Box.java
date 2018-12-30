package NonogramStructure;

import java.util.Objects;

public class Box {
    private final int posX;
    private final int posY;
    private State state;

    private int markL = -1;
    private int markR = -1;
    private int markT = -1;
    private int markB = -1;

    public Box(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        state = State.UNKNOWN;
    }

    //---------------------//
    // Getters and setters //
    //---------------------//
    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }


    public int getMarkL() {
        return markL;
    }

    public void setMarkL(int markL) {
        this.markL = markL;
    }

    public int getMarkR() {
        return markR;
    }

    public void setMarkR(int markR) {
        this.markR = markR;
    }

    public int getMarkT() {
        return markT;
    }

    public void setMarkT(int markT) {
        this.markT = markT;
    }

    public int getMarkB() {
        return markB;
    }

    public void setMarkB(int markB) {
        this.markB = markB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return posX == box.posX &&
                posY == box.posY &&
                state == box.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY, state);
    }

    @Override
    public String toString() {
        return "Box{X/Y=" + posX + "/" + posY +
                ", state=" + state +
                '}';
    }
}

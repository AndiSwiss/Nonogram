package NonogramStructure;

import java.util.Objects;

public class Box {
    private int posX;
    private int posY;
    private State state;

    Box(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        state = State.UNKNOWN;
    }


    public void setState(State state) {
        this.state = state;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public State getState() {
        return state;
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

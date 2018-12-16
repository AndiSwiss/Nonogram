package NonogramStructure;

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
}

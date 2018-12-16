package Solver;

import java.util.List;

public class Line {
    private List<Integer> numbers;
    private Box[] boxes;

    public Line(List<Integer> numbers, int size) {
        this.numbers = numbers;
        boxes = new Box[size];
    }


    public List<Integer> getNumbers() {
        return numbers;
    }

    public Box[] getBoxes() {
        return boxes;
    }
}

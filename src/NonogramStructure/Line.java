package NonogramStructure;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<Number> numbers;
    private List<Box> boxes;
    private Direction direction;
    private int lineNumber;

    public Line(List<Number> numbers, int lineNumber, int size, Direction direction) {
        this.numbers = numbers;
        this.lineNumber = lineNumber;
        this.direction = direction;
        boxes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (direction == Direction.HORIZONTAL) {

                boxes.add(new Box(i, lineNumber));
            } else if (direction == Direction.VERTICAL) {
                boxes.add(new Box(lineNumber, i));
            } else {
                throw new IllegalArgumentException("Illegal Direction found in the Line-Constructor. " +
                        "Direction: " + direction);
            }
        }
    }

    //----------------//
    // Custom Methods //
    //----------------//
    public int sizeBoxes() {
        return boxes.size();
    }

    public int sizeNumber() {
        return numbers.size();
    }


    //---------//
    // Getters //
    //---------//
    public List<Number> getNumbers() {
        return numbers;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}

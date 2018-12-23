package NonogramStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line {
    private NumberLine numbers;
    private List<Box> boxes;
    private Direction direction;
    private int lineNumber;

    /**
     * Constructor
     *
     * @param boxes     List<Box>
     * @param numbers   NumberLine
     * @param direction Direction
     */
    public Line(List<Box> boxes, NumberLine numbers, Direction direction) {
        this.boxes = boxes;
        this.numbers = numbers;
        this.direction = direction;

        if (direction == Direction.HORIZONTAL) {
            lineNumber = boxes.get(0).getPosY();
            // Check the lineNumber from the first box. And check, whether all the other passed along boxes
            // have the same line number:
            for (Box b : boxes) {
                if (lineNumber != b.getPosY()) {
                    throw new IllegalArgumentException("Error in Line-Constructor of VERTICAL line: " +
                            "not all passed boxes have the same line-number! \n"
                            + " expected line number: " + lineNumber + ", actual lineNumber from box: " + b.getPosY()
                            + ". X-Value of that box is: " + b.getPosX());
                }
            }
        } else if (direction == Direction.VERTICAL) {
            lineNumber = boxes.get(0).getPosX();
            // Check the lineNumber from the first box. And check, whether all the other passed along boxes
            // have the same line number:
            for (Box b : boxes) {
                if (lineNumber != b.getPosX()) {
                    throw new IllegalArgumentException("Error in Line-Constructor of HORIZONTAL line: " +
                            "not all passed boxes have the same line-number! \n"
                            + " expected line number: " + lineNumber + ", actual lineNumber from box: " + b.getPosX()
                            + ". Y-Value of that box is: " + b.getPosY());
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal Direction found in the Line-Constructor. " +
                    "Direction: " + direction + ". This constructor should only be use for HORIZONTAL lines! " +
                    "Please use the other constructor for the VERTICAL lines, so that the boxes are not duplicated! ");
        }
    }

    /**
     * For constructing a single line for test-methods <br>
     * Do NOT use for construction of a whole nonogram! Because otherwise, the horizontalLines
     * would NOT contain the same box-references as the verticalLines!
     *
     * @param lineNumber         int
     * @param lengthForEmptyLine int
     * @param direction          Direction
     */
    public Line(int lineNumber, int lengthForEmptyLine, Direction direction) {

        boxes = new ArrayList<>();
        if (direction == Direction.HORIZONTAL) {
            for (int x = 0; x < lengthForEmptyLine; x++) {
                boxes.add(new Box(x, lineNumber));
            }
        } else {
            for (int y = 0; y < lengthForEmptyLine; y++) {
                boxes.add(new Box(lineNumber, y));
            }
        }

        // create the empty list of numbers:
        List<Number> emptyNumbersList = new ArrayList<>();
        numbers = new NumberLine(emptyNumbersList);

        this.direction = direction;
        this.lineNumber = lineNumber;

    }

    //----------------//
    // Custom Methods //
    //----------------//
    public int getBoxesSize() {
        return boxes.size();
    }

    public int getNumbersSize() {
        return numbers.size();
    }

    public Box getBox(int i) {
        return boxes.get(i);
    }

    public boolean areAllNumbersCrossedOut() {
        return numbers.areAllCrossedOut();
    }


    //---------//
    // Getters //
    //---------//
    public NumberLine getNumbers() {
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


    // todo: write a smart equals method -> should get useful for comparing a solution file with current progress...
    // todo: it would be important to throw smart errors, so I can see, WHAT exactly would be different!

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return lineNumber == line.lineNumber &&
                Objects.equals(numbers, line.numbers) &&
                Objects.equals(boxes, line.boxes) &&
                direction == line.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers, boxes, direction, lineNumber);
    }


    // todo: write a smart toString-method!
    @Override
    public String toString() {
        return "Line{" +
                "numbers=" + numbers +
                ", boxes=" + boxes +
                ", direction=" + direction +
                ", lineNumber=" + lineNumber +
                '}';
    }
}

package NonogramStructure;

import java.util.List;

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
    Line(List<Box> boxes, NumberLine numbers, Direction direction) {
        this.boxes = boxes;
        this.numbers = numbers;
        this.direction = direction;

        if (direction == Direction.HORIZONTAL) {
            lineNumber = boxes.get(0).getPosY()  ;
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
            lineNumber = boxes.get(0).getPosX()  ;
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
}

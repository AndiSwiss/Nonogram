package NonogramStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line {
    private NumberLine numberLine;
    private List<Box> boxes;
    private Direction direction;
    private int lineNumber;
    private boolean containsMarks;
    private boolean lineIsReversed;

    /**
     * Constructor
     *
     * @param boxes      List<Box>
     * @param numberLine NumberLine
     * @param direction  Direction
     */
    public Line(List<Box> boxes, NumberLine numberLine, Direction direction) {
        this.boxes = boxes;
        this.numberLine = numberLine;
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
        numberLine = new NumberLine(emptyNumbersList);

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
        return numberLine.size();
    }

    public Box getBox(int i) {
        return boxes.get(i);
    }

    public boolean areAllNumbersCrossedOut() {
        return numberLine.areAllCrossedOut();
    }


    /**
     * @return the Line, but reversed. So the last position equals to the first position
     */
    public Line reversed() {
        // boxes:
        List<Box> reversedBoxes = new ArrayList<>();
        for (Box b : boxes) {
            reversedBoxes.add(0, b);
        }
        // numberLine:
        List<Number> reversedNumbers = new ArrayList<>();
        for (Number n : numberLine.getNumbers()) {
            reversedNumbers.add(0, n);
        }
        NumberLine reversedNumberLine = new NumberLine(reversedNumbers);

        Line reversed = new Line(reversedBoxes, reversedNumberLine, direction);
        reversed.lineIsReversed = true;

        // copy all the other fields of this line:
        reversed.lineNumber = lineNumber;
        reversed.containsMarks = containsMarks;

        return reversed;
    }


    /**
     * @param boxNr box-nr
     * @return The mark in the corresponding direction (horizontal/vertical and normal/reversed)
     * -> markL/markR/markT/markB
     */
    public int getMarkForBox(int boxNr) {
        if (direction == Direction.HORIZONTAL) {
            if (!lineIsReversed) {
                return getBox(boxNr).getMarkL();
            } else {
                return getBox(boxNr).getMarkR();
            }
        } else {
            if (!lineIsReversed) {
                return getBox(boxNr).getMarkT();
            } else {
                return getBox(boxNr).getMarkB();
            }
        }
    }

    /**
     * @param boxNr box-nr
     * @param mark  The mark to be set in the corresponding direction (horizontal/vertical and normal/reversed)
     *              -> markL/markR/markT/markB
     */
    public void setMarkForBox(int boxNr, int mark) {
        if (direction == Direction.HORIZONTAL) {
            if (!lineIsReversed) {
                getBox(boxNr).setMarkL(mark);
            } else {
                getBox(boxNr).setMarkR(mark);
            }
        } else {
            if (!lineIsReversed) {
                getBox(boxNr).setMarkT(mark);
            } else {
                getBox(boxNr).setMarkB(mark);
            }
        }
    }


    /**
     * @param numberIndex numberIndex of the number in question
     * @return The position of the first possible occurrence of this number. Returns -1, if the number was not found.
     */
    public int getFirstPossiblePositionForNumber(int numberIndex) {
        for (int i = 0; i < getBoxesSize(); i++) {
            if (getMarkForBox(i) == numberIndex) {
                return i;
            }
        }
        return -1;
    }


    /**
     * @param numberIndex numberIndex of the number in question
     * @return The position of the last possible occurrence of this number. Returns -1, if the number was not found.
     */
    public int getLastPossiblePositionForNumber(int numberIndex) {
        Line reversed = reversed();
        for (int i = 0; i < getBoxesSize(); i++) {
            // for the reversed logic:
            int reversedNumberIndex = getNumbersSize() - numberIndex - 1;

            if (reversed.getMarkForBox(i) == reversedNumberIndex) {
                // and reverse the found box again:
                return getBoxesSize() - i - 1;
            }
        }
        return -1;
    }


    /**
     * @return true, if the box has the same mark
     */

    public boolean boxHasSameOppositeMark(int boxNr) {
        int forwardMark = getMarkForBox(boxNr);

        if (forwardMark == -1) {
            return false;
        }

        int backwardMark;
        if (direction == Direction.HORIZONTAL) {
            backwardMark = getBox(boxNr).getMarkR();
        } else {
            backwardMark = getBox(boxNr).getMarkB();
        }

        // remember: in the reversed line, the numberIndexes are also reversed:
        return forwardMark == getNumbersSize() - backwardMark - 1;
    }


    //-------------------//
    // Getters & Setters //
    //-------------------//
    public NumberLine getNumberLine() {
        return numberLine;
    }

    public List<Number> getNumbers() {
        return numberLine.getNumbers();
    }

    public Number getNumber(int i) {
        return numberLine.get(i);
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

    public boolean containsMarks() {
        return containsMarks;
    }

    public void setContainsMarks(boolean containsMarks) {
        this.containsMarks = containsMarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return lineNumber == line.lineNumber &&
                Objects.equals(numberLine, line.numberLine) &&
                Objects.equals(boxes, line.boxes) &&
                direction == line.direction;
    }

    public boolean isLineReversed() {
        return lineIsReversed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberLine, boxes, direction, lineNumber);
    }

    @Override
    public String toString() {
        StringBuilder boxString = new StringBuilder();
        for (Box box : boxes) {
            switch (box.getState()) {
                case UNKNOWN:
                    boxString.append('\u2591');
                    break;
                case BLACK:
                    boxString.append('\u258b');
                    break;
                case WHITE:
                    boxString.append(' ');
                    break;
                default:
                    throw new IllegalArgumentException("Unknown State in toString-Method of Line: " + box.getState());
            }
        }

        StringBuilder numbersString = new StringBuilder();
        // let's assume, that there is a maximum of 10 sideNumbers:
        for (int i = 0; i < 10 - numberLine.size(); i++) {
            numbersString.append("   ");
        }
        for (int i = 0; i < numberLine.size(); i++) {
            if (numberLine.get(i).getN() < 10) {
                numbersString.append(' ');
            }
            numbersString.append(numberLine.get(i)).append(' ');
        }
        return numbersString + "|"
                + boxString +
                "| lineNr=" + lineNumber +
                ", dir=" + direction;
    }
}

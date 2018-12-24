package Solver;

import NonogramStructure.*;
import NonogramStructure.Number;

import java.util.List;

public class Solver {

    public void start(Nonogram no) {

        boolean horSuccess = strategy1AllHorizontal(no);
        boolean verSuccess = strategy1AllVertical(no);

        // todo: add a separate method which checks, whether a box has no marks AND is not in between the same marking-number. If found, mark box State.WHITE


    }

    public boolean strategy1AllHorizontal(Nonogram no) {
        boolean changedSomething = false;

        for (Line hLine : no.getHorizontalLines()) {
            boolean change = strategy1(hLine);
            if (change) {
                changedSomething = true;
            }
        }
        return changedSomething;
    }

    public boolean strategy1AllVertical(Nonogram no) {
        boolean changedSomething = false;

        for (Line vLine : no.getVerticalLines()) {
            boolean change = strategy1(vLine);
            if (change) {
                changedSomething = true;
            }
        }
        return changedSomething;
    }

    /**
     * Strategy 1: look at the EMPTY line's numbers and try to figure out if you can draw some boxes:
     *
     * @param line Line
     * @return true, if it changed a state of a box. False if not.
     */
    public boolean strategy1(Line line) {

        boolean changedSomething = false;

        markLine(line);

        // check, if a box has the same mark in markL and markR (or markT and markB in VERTICAL lines):
        for (Box box : line.getBoxes()) {
            if (box.hasSameHorizontalMark() || box.hasSameVerticalMark()) {
                if (box.getState() == State.UNKNOWN) {
                    box.setState(State.BLACK);
                    changedSomething = true;
                } else if (box.getState() == State.WHITE) {
                    throw new IllegalArgumentException("This box was marked white, but with this algorithm, it was" +
                            "determined to be black! Box: " + box);
                }
            }
        }

        return changedSomething;


    }

    /**
     * Marks a line with the number (similar to the blue line in the iPhone-app) <br>
     * Note that -1 in a mark means: no mark. Any other integer stands for the index of the corresponding number.
     *
     * @param line Line
     */
    public void markLine(Line line) {
        int position = 0;
        List<Number> numbers = line.getNumbers();

        // from left (or top)
        for (int numberIndex = 0; numberIndex < numbers.size(); numberIndex++) {
            Number number = numbers.get(numberIndex);

            // check if no BLACK box is on the left (or top) side, if there is, move one block:
            position = moveForwardIfABlackBoxIsOnThePositionToCheck(line, position, position - 1);

            // check if there is enough (non-WHITE!) space for placing the mark for the number:
            // else move the position and repeat the loop:
            // & check if no BLACK box is on the right (or bottom) side. If there is, move one block and repeat this whole loop:
            position = moveForwardIfAWhiteSpaceWasFoundOrIfABlackBoxIsOnTheRightOrBottomSide(line, position, number);

            // mark the number
            for (int l = 0; l < number.getN(); l++) {
                if (line.getDirection() == Direction.HORIZONTAL) {
                    line.getBox(position).setMarkL(numberIndex);
                } else {
                    line.getBox(position).setMarkT(numberIndex);
                }
                position++;
            }
            // and move one space in between numbers:
            position++;
        }


        // todo: repeat the same code from above, but in reverse order!
        // from right (or bottom)
        position = line.getBoxesSize() - 1;
        for (int i = numbers.size() - 1; i >= 0; i--) {
            Number number = numbers.get(i);
            for (int l = 0; l < number.getN(); l++) {
                if (line.getDirection() == Direction.HORIZONTAL) {
                    line.getBox(position).setMarkR(i);
                } else {
                    line.getBox(position).setMarkB(i);
                }
                position--;
            }
            // and move one space in between numbers:
            position--;
        }
    }

    private int moveForwardIfAWhiteSpaceWasFoundOrIfABlackBoxIsOnTheRightOrBottomSide(Line line, int position, Number number) {
        for (int l = 0; l < number.getN(); l++) {
            // if a WHITE box was found:
            if (line.getBox(position + l).getState() == State.WHITE) {
                System.out.println("A white box was found on the left (or top) at position " + (position + l) + ", so the code moved forward to that new position + 1");
                // advance to the next non-white position:
                position += l + 1;
                // repeat this whole loop again
                l = 0;
            }

            // check if no BLACK box is on the right (or bottom) side of the current number. If there is, move block and repeat this whole loop:
            int newPosition = moveForwardIfABlackBoxIsOnThePositionToCheck(line, position, position + l + 1);
            if (newPosition != position) {
                position = newPosition;
                // repeat the whole loop from the beginning:
                l = 0;
            }

        }
        return position;
    }

    private int moveForwardIfABlackBoxIsOnThePositionToCheck(Line line, int currentPosition, int positionToCheck) {

        if (positionToCheck == currentPosition) {
            throw new IllegalArgumentException("Error! positionToCheck == currentPosition = " + positionToCheck);
        } else if (positionToCheck >= line.getBoxesSize()) {
            throw new IllegalArgumentException("Error! positionToCheck >= line.getBoxesSize()!  positionToCheck = " + positionToCheck);
        }

        String text;
        if (positionToCheck - currentPosition < 0) {
            if (line.getDirection() == Direction.HORIZONTAL) {
                text = "left";
            } else {
                text = "top";
            }
        } else {
            if (line.getDirection() == Direction.HORIZONTAL) {
                text = "right";

            } else {
                text = "bottom";
            }
        }

        if (positionToCheck >= 0) {
            if (line.getBox(positionToCheck).getState() == State.BLACK) {
                System.out.println("A BLACK box was found in moveForwardIfABlackBoxIsOnThePositionToCheck(..) " +
                        "on the " + text + " side at position " + positionToCheck + ".");

                // figure out the new position:
                if (positionToCheck - currentPosition < 0) {
                    currentPosition++;
                } else {
                    // move to the checked position:
                    currentPosition = positionToCheck;
                }
            }
        }
        return currentPosition;
    }
}


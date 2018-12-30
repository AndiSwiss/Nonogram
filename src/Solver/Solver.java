package Solver;

import NonogramStructure.*;
import NonogramStructure.Number;

import java.util.List;

public class Solver {

    public void start(Nonogram no) {

        boolean horSuccess = strategy1AllHorizontal(no);
        boolean verSuccess = strategy1AllVertical(no);

        // todo: add a separate method which checks, whether a box has no marks AND is not in between the same
        //  marking-number. If found, mark box State.WHITE
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

        markLine(line);

        boolean changedSomething = markBoxesWhichHaveSameMarksInOppositeDirection(line);

        return changedSomething;
    }

    /**
     * @param line Line
     * @return True, if a new box was marked BLACK
     */
    public boolean markBoxesWhichHaveSameMarksInOppositeDirection(Line line) {
        boolean changedSomething = false;

        // check, if a box has the same mark in markL and markR (or markT and markB in VERTICAL lines):
        List<Box> boxes = line.getBoxes();
        for (int boxNr = 0; boxNr < boxes.size(); boxNr++) {
            if (line.boxHasSameOppositeMark(boxNr)) {
                Box box = boxes.get(boxNr);
                if (box.getState() == State.UNKNOWN) {
                    box.setState(State.BLACK);
                    changedSomething = true;
                } else if (box.getState() == State.WHITE) {
                    throw new IllegalArgumentException("This box had State.WHITE, but with this algorithm, it was" +
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

        markLineInOneDirection(line);

        Line reversed = line.reversed();
        markLineInOneDirection(reversed);

        line.setContainsMarks(true);
    }

    public void markLineInOneDirection(Line line) {
        List<Number> numbers = line.getNumbers();

        System.out.println("---- Creating Marks in " + line.getDirection() + "line " + line.getLineNumber() +
                ", isLineReversed=" + line.isLineReversed() + " ----");
        int position = 0;

        // from left (or top)
        for (int numberIndex = 0; numberIndex < numbers.size(); numberIndex++) {
            Number number = numbers.get(numberIndex);
            System.out.println("checking number " + number.getN() + " (number index=" + numberIndex + ")");

            LabelBreakForCheck:
            if (line.containsMarks()) {
                // first check, whether the number still exists (it might have been erased by another number-marking
                // -> this happened as documented in SolverTest_On_Nonogram1.markLineInOneDirection_problem3()
                boolean foundTheMark = false;
                for (int j = 0; j < line.getBoxesSize(); j++) {
                    if (numberIndex == line.getMarkForBox(j)) {
                        foundTheMark = true;
                    }
                }
                if (!foundTheMark) {
                    System.out.println("Breaking out to 'LabelBreakForCheck:', because for the following mark was not found: " +
                            line.getDirection() + " Line-Nr=" + line.getLineNumber() +
                            ", oldPosition=" + position + ", isLineReversed=" + line.isLineReversed() +
                            ", numberIndex=" + numberIndex);
                    break LabelBreakForCheck;
                }

                // if not already past the mark, move forward to the existing mark
                //  -> that was the problem in SolverTest/markLineInOneDirection_problem2()
                // so: first check, if already past the mark:
                boolean passed = checkIfAlreadyPassedTheMark(line, position, numberIndex);
                if (!passed) {
                    // move forward to the existing mark,
                    while (numberIndex != line.getMarkForBox(position)) {
                        position++;
                        System.out.println("Moved towards the previous mark-position: " +
                                line.getDirection() + " Line-Nr=" + line.getLineNumber() +
                                ", newPosition=" + position + ", isLineReversed=" + line.isLineReversed() +
                                ", numberIndex=" + numberIndex);
                    }
                }
            }

            int newPosition = position;
            do {
                // renew position (important after running one time through this do-while-loop):
                position = newPosition;

                // check if no BLACK box is on the left (or top) side, if there is, move one block:
                int positionToCheck = newPosition - 1;
                newPosition = moveIfABlackBoxIsOnThePositionToCheck(line, newPosition, positionToCheck);

                // check if there is enough (non-WHITE!) space for placing the mark for the number:
                // else move the position and repeat the loop:
                newPosition = moveIfAWhiteSpaceWasFound(line, newPosition, number);

                // check if no BLACK box is on the right (or bottom) side OF THE WHOLE NUMBER.
                // If there is, move to that position and repeat this whole loop:
                positionToCheck = newPosition + number.getN();
                newPosition = moveIfABlackBoxIsOnThePositionToCheck(line, newPosition, positionToCheck);

                if (line.containsMarks()) {
                    // delete wrong marks:
                    for (int j = 0; j < newPosition; j++) {
                        deleteAMarkIfItEqualsTheGivenNumberIndex(line, j, numberIndex);
                    }
                }

            } while (newPosition != position);

            position = newPosition;

            // mark the number and advance the position:
            position = markANumberAndAdvancePosition(line, position, numberIndex, number);
            System.out.println();
            // and move one space in between numbers:
            position++;
        }
    }

    public boolean checkIfAlreadyPassedTheMark(Line line, int position, int numberIndex) {
        for (int i = 0; i <= position; i++) {
            if (line.getMarkForBox(i) == numberIndex) {
                return true;
            }
        }
        return false;
    }

    public void deleteAMarkIfItEqualsTheGivenNumberIndex(Line line, int position, int numberIndex) {

        if (line.getMarkForBox(position) == numberIndex) {
            // delete the mark (by setting it to minus 1):
            line.setMarkForBox(position, -1);
            System.out.println("Deleted a mark: " + line.getDirection() + " line-nr " + line.getLineNumber() +
                    ", position=" + position + ", numberIndex=" + numberIndex +
                    ". isLineReversed=" + line.isLineReversed());
        }
    }


    public int markANumberAndAdvancePosition(Line line, int position, int numberIndex, Number number) {
        System.out.print("marking in the " + line.getDirection().toString().toLowerCase() + " line nr " +
                line.getLineNumber() + " the number " + number.getN() + " at positions: ");
        for (int l = 0; l < number.getN(); l++) {
            System.out.print(position + " ");
            line.setMarkForBox(position, numberIndex);

            position++;
        }
        return position;
    }

    public int moveIfAWhiteSpaceWasFound(Line line, int position, Number number) {
        for (int l = 0; l < number.getN(); l++) {
            // if a WHITE box was found:

            int positionToCheck = position + l;

            if (line.getBox(positionToCheck).getState() == State.WHITE) {
                System.out.println("A white box was found on the left (or top) at position " + positionToCheck + ", " +
                        "so the code moved forward to that new position + 1. lineNumber=" + line.getLineNumber() +
                        ", isLineReversed=" + line.isLineReversed() + ", ");
                // advance to the next non-white position:
                position += l + 1;
                // repeat this whole loop again
                l = 0;
            }
        }
        return position;
    }

    public int moveIfABlackBoxIsOnThePositionToCheck(Line line, int currentPosition, int positionToCheck) {

        // stop checking, if the checking is at the beginning or the end:
        if (positionToCheck < 0 || positionToCheck >= line.getBoxesSize()) {
            return currentPosition;
        }

        // error checking
        if (positionToCheck == currentPosition) {
            throw new IllegalArgumentException("Error! positionToCheck == currentPosition = " + positionToCheck);
        }

        // only for terminal-output:
        String text;
        if (positionToCheck - currentPosition < 0) {
            if (line.getDirection() == Direction.HORIZONTAL) text = "left";
            else text = "top";
        } else {
            if (line.getDirection() == Direction.HORIZONTAL) text = "right";
            else text = "bottom";
        }

        if (line.getBox(positionToCheck).getState() == State.BLACK) {
            System.out.println("A BLACK box was found in moveIfABlackBoxIsOnThePositionToCheck(..) " +
                    "in " + line.getDirection().toString().toLowerCase() + " line-nr " + line.getLineNumber() + " on the "
                    + text + " side. currentPosition=" + currentPosition + ", positionToCheck=" + positionToCheck +
                    ", isLineReversed=" + line.isLineReversed());

            // move the position:
            currentPosition++;
        }
        return currentPosition;
    }
}


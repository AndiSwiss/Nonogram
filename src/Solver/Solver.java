package Solver;

import NonogramStructure.*;
import NonogramStructure.Number;

import java.util.List;

public class Solver {

    public void start(Nonogram no) {

        boolean horSuccess = strategy1AllHorizontal(no);
        boolean verSuccess = strategy1AllVertical(no);

        // todo: add a separate method which checks, whether a box has no marks AND is not in between the same marking-number. If found, mark box State.WHITE


        // todo: there is a problem, when strategy1AllHorizontal() or strategy1AllVertical() is running twice on some examples -> investigate!
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
    private boolean markBoxesWhichHaveSameMarksInOppositeDirection(Line line) {
        boolean changedSomething = false;
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
        markLineInOneDirection(line, true);
        markLineInOneDirection(line, false);
    }

    private void markLineInOneDirection(Line line, boolean forward) {
        List<Number> numbers = line.getNumbers();

        int position;
        if (forward) {
            position = 0;
        } else {
            position = line.getBoxesSize() - 1;
        }

        // from left (or top)
        for (int i = 0; i < numbers.size(); i++) {
            int numberIndex;
            if (forward) {
                numberIndex = i;
            } else {
                numberIndex = numbers.size() - 1 - i;
            }
            Number number = numbers.get(numberIndex);

            System.out.println("checking number " + number.getN() + " (number index=" + numberIndex + ")");
            // check if no BLACK box is on the left (or top) side, if there is, move one block:
            int positionToCheck;
            if (forward) {
                positionToCheck = position - 1;
            } else {
                positionToCheck = position + 1;
            }
            position = moveIfABlackBoxIsOnThePositionToCheck(line, position, positionToCheck, forward);

            // check if there is enough (non-WHITE!) space for placing the mark for the number:
            // else move the position and repeat the loop:
            // & check if no BLACK box is on the right (or bottom) side. If there is, move one block and repeat this whole loop:
            position = moveIfAWhiteSpaceWasFoundOrIfABlackBoxIsOnTheRightOrBottomSide(line, position, number, forward);

            // mark the number
            System.out.print("marking in the " + line.getDirection().toString().toLowerCase() + " line nr " + line.getLineNumber()
                    + " the number " + number.getN() + " at positions: ");
            for (int l = 0; l < number.getN(); l++) {
                System.out.print(position + " ");
                Box box = line.getBox(position);
                if (line.getDirection() == Direction.HORIZONTAL) {
                    if (forward) {
                        box.setMarkL(numberIndex);
                    } else {
                        box.setMarkR(numberIndex);
                    }
                } else {
                    if (forward) {
                        box.setMarkT(numberIndex);
                    } else {
                        box.setMarkB(numberIndex);
                    }
                }

                if (forward) {
                    position++;
                } else {
                    position--;
                }
            }
            System.out.println();
            // and move one space in between numbers:
            if (forward) {
                position++;
            } else {
                position--;
            }
        }
    }

    private int moveIfAWhiteSpaceWasFoundOrIfABlackBoxIsOnTheRightOrBottomSide(Line line, int position, Number number, boolean forward) {
        for (int l = 0; l < number.getN(); l++) {
            // if a WHITE box was found:

            int positionToCheck;
            if (forward) {
                positionToCheck = position + l;
            } else {
                positionToCheck = position - l;
            }

            if (line.getBox(positionToCheck).getState() == State.WHITE) {
                System.out.println("A white box was found on the left (or top) at position " + positionToCheck + ", " +
                        "so the code moved " + (forward ? "forward" : "backward") + " to that new position + 1");
                if (forward) {
                    // advance to the next non-white position:
                    position += l + 1;
                } else {
                    // advance in reverse direction to the next non-white position:
                    position -= l + 1;
                }
                // repeat this whole loop again
                l = 0;
            }

            // check if no BLACK box is on the right (or bottom) side of the current number (or on the left (or top) for reversed direction).
            // If there is, move block and repeat this whole loop:
            if (forward) {
                positionToCheck++;
            } else {
                positionToCheck--;
            }
            int newPosition = moveIfABlackBoxIsOnThePositionToCheck(line, position, positionToCheck, forward);
            if (newPosition != position) {
                position = newPosition;
                // repeat the whole loop from the beginning:
                l = 0;
            }
        }
        return position;
    }

    private int moveIfABlackBoxIsOnThePositionToCheck(Line line, int currentPosition, int positionToCheck, boolean forward) {

        // stop checking, if the checking is at the beginning or the end:
        if (positionToCheck < 0 || positionToCheck >= line.getBoxesSize()) {
            return currentPosition;
        }

        // error checking
        if (positionToCheck == currentPosition) {
            throw new IllegalArgumentException("Error! positionToCheck == currentPosition = " + positionToCheck);
        }

        String text;
        if (positionToCheck - currentPosition < 0) {
            if (line.getDirection() == Direction.HORIZONTAL) text = "left";
            else text = "top";
        } else {
            if (line.getDirection() == Direction.HORIZONTAL) text = "right";
            else text = "bottom";
        }

        if (line.getBox(positionToCheck).getState() == State.BLACK) {
            System.out.print("A BLACK box was found in moveIfABlackBoxIsOnThePositionToCheck(..) " +
                    "in " + line.getDirection().toString().toLowerCase() + " line-nr " + line.getLineNumber() + " on the " + text + " side at position " + positionToCheck + ".");

            // move the position:
            if (forward) {
                currentPosition++;
            } else {
                currentPosition--;
            }
        }
        return currentPosition;
    }
}


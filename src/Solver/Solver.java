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


        // todo: there is a problem, when strategy1AllHorizontal() or strategy1AllVertical() is running twice on
        //  some examples -> investigate!


        // todo: drastically simplify everything by:
        //  - In order to handle the solver-elements just once, I have to get a line-getter 'reversed' in Line.java
        //  -> so I would not have to reverse loops anymore
        //  - And in order to get rid of horizontal/vertical checking, just do the following:
        //  - create a line-getter 'forward' and one line-getter 'reverse'
        //  - + create a getMarkForBox(int i) and setMarkForBox(int i, int mark)
        //  - -> that would automatically return the correct mark (markL / markR / markT / markB)
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
        for (Box box : line.getBoxes()) {
            if (box.hasSameHorizontalMark() || box.hasSameVerticalMark()) {
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

        markLineInOneDirection(line, true);
        markLineInOneDirection(line, false);

        line.setContainsMarks(true);
    }

    public void markLineInOneDirection(Line line, boolean forward) {
        List<Number> numbers = line.getNumbers();

        System.out.println("---- Creating Marks in " +
                ((line.getDirection() == Direction.HORIZONTAL) ? "horizontal" : "vertical") +
                "line " + line.getLineNumber() + ", forward=" + forward + " ----");
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

            LabelBreakForCheck:
            if (line.containsMarks()) {
                // first check, whether the number still exists (it might have been erased by another number-marking
                // -> this happened as documented in SolverTest_On_Nonogram1.markLineInOneDirection_problem3()
                boolean foundTheMark = false;
                for (int j = 0; j < line.getBoxesSize(); j++) {
                    if (numberIndex == getMarkInTCorrectDirectionFromABox(line, j, forward)) {
                        foundTheMark = true;
                    }
                }
                if (!foundTheMark) {
                    System.out.println("Breaking out to 'LabelBreakForCheck:', because for the following mark was not found: " +
                            ((line.getDirection() == Direction.HORIZONTAL) ? "horizontal" : "vertical") +
                            " Line-Nr=" + line.getLineNumber() +
                            ", oldPosition=" + position + ", forward=" + forward + ", numberIndex=" + numberIndex);
                    break LabelBreakForCheck;
                }

                // if not already past the mark, move forward to the existing mark
                //  -> that was the problem in SolverTest/markLineInOneDirection_problem2()
                // so: first check, if already past the mark:
                boolean passed = checkIfAlreadyPassedTheMark(line, position, numberIndex, forward);
                if (!passed) {
                    // move forward to the existing mark,
                    while (numberIndex != getMarkInTCorrectDirectionFromABox(line, position, forward)) {
                        System.out.println("Moved towards the previous mark-position: " +
                                ((line.getDirection() == Direction.HORIZONTAL) ? "horizontal" : "vertical") +
                                " Line-Nr=" + line.getLineNumber() +
                                ", oldPosition=" + position + ", forward=" + forward + ", numberIndex=" + numberIndex);

                        if (forward) {
                            position++;
                        } else {
                            position--;
                        }
                    }
                }
            }

            int newPosition = position;
            do {
                // renew position (important after running one time through this do-while-loop):
                position = newPosition;

                // check if no BLACK box is on the left (or top) side, if there is, move one block:
                int positionToCheck;
                if (forward) {
                    positionToCheck = newPosition - 1;
                } else {
                    positionToCheck = newPosition + 1;
                }
                newPosition = moveIfABlackBoxIsOnThePositionToCheck(line, newPosition, positionToCheck, forward);

                // check if there is enough (non-WHITE!) space for placing the mark for the number:
                // else move the position and repeat the loop:
                newPosition = moveIfAWhiteSpaceWasFound(line, newPosition, number, forward);

                // check if no BLACK box is on the right (or bottom) side OF THE WHOLE NUMBER.
                // If there is, move to that position and repeat this whole loop:
                if (forward) {
                    positionToCheck = newPosition + number.getN();
                } else {
                    positionToCheck = newPosition - number.getN();
                }
                newPosition = moveIfABlackBoxIsOnThePositionToCheck(line, newPosition, positionToCheck, forward);

                if (line.containsMarks()) {
                    // delete wrong marks:
                    int start;
                    int end;
                    if (forward) {
                        start = 0;
                        end = newPosition - 1;
                    } else {
                        start = newPosition + 1;
                        end = line.getBoxesSize() - 1;
                    }

                    for (int j = start; j <= end; j++) {
                        deleteAMarkIfItEqualsTheGivenNumberIndex(line, forward, j, numberIndex);
                    }
                }

            } while (newPosition != position);

            position = newPosition;

            // mark the number and advance the position:
            position = markANumberAndAdvancePosition(line, forward, position, numberIndex, number);
            System.out.println();
            // and move one space in between numbers:
            if (forward) {
                position++;
            } else {
                position--;
            }
        }
    }

    public boolean checkIfAlreadyPassedTheMark(Line line, int position, int numberIndex, boolean forward) {
        int start;
        int end;
        if (forward) {
            start = 0;
            end = position;
        } else {
            start = position;
            end = line.getBoxesSize() - 1;
        }
        for (int i = start; i <= end; i++) {
            Box box = line.getBox(i);
            if (line.getDirection() == Direction.HORIZONTAL) {
                if (forward) {
                    if (box.getMarkL() == numberIndex) {
                        return true;
                    }
                } else {
                    if (box.getMarkR() == numberIndex) {
                        return true;
                    }
                }
            } else {
                if (forward) {
                    if (box.getMarkT() == numberIndex) {
                        return true;
                    }
                } else {
                    if (box.getMarkB() == numberIndex) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void deleteAMarkIfItEqualsTheGivenNumberIndex(Line line, boolean forward, int position,
                                                         int numberIndex) {
        Box box = line.getBox(position);
        if (line.getDirection() == Direction.HORIZONTAL) {
            if (forward) {
                if (box.getMarkL() == numberIndex) {
                    box.setMarkL(-1);
                    System.out.println("Deleted a mark: horizontal line-nr " + line.getLineNumber() + ", position=" +
                            position + ", numberIndex=" + numberIndex + " forward = true");
                }
            } else {
                if (box.getMarkR() == numberIndex) {
                    box.setMarkR(-1);
                    System.out.println("Deleted a mark: horizontal line-nr " + line.getLineNumber() + ", position=" +
                            position + ", numberIndex=" + numberIndex + " forward = false");
                }
            }
        } else {
            if (forward) {
                if (box.getMarkT() == numberIndex) {
                    box.setMarkT(-1);
                    System.out.println("Deleted a mark: vertical line-nr " + line.getLineNumber() + ", position=" +
                            position + ", numberIndex=" + numberIndex + " forward = true");
                }
            } else {
                if (box.getMarkB() == numberIndex) {
                    box.setMarkB(-1);
                    System.out.println("Deleted a mark: vertical line-nr " + line.getLineNumber() + ", position=" +
                            position + ", numberIndex=" + numberIndex + " forward = false");
                }
            }
        }
    }

    public int getMarkInTCorrectDirectionFromABox(Line line, int position, boolean forward) {
        Box box = line.getBox(position);
        if (line.getDirection() == Direction.HORIZONTAL) {
            if (forward) {
                return box.getMarkL();
            } else {
                return box.getMarkR();
            }
        } else {
            if (forward) {
                return box.getMarkT();
            } else {
                return box.getMarkB();
            }
        }
    }

    public int markANumberAndAdvancePosition(Line line, boolean forward, int position, int numberIndex, Number
            number) {
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
        return position;
    }

    public int moveIfAWhiteSpaceWasFound(Line line, int position, Number number, boolean forward) {
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
                        "so the code moved " + (forward ? "forward" : "backward") + " to that new position +/- 1");
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
        }
        return position;
    }

    public int moveIfABlackBoxIsOnThePositionToCheck(Line line, int currentPosition, int positionToCheck,
                                                     boolean forward) {

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
                    ", forward=" + forward);

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


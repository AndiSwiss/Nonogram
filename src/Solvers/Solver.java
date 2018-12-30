package Solvers;

import NonogramStructure.*;
import NonogramStructure.Number;

import java.util.List;

public class Solver {

    //----------------------------------------------//
    // For all strategies in the different solvers! //
    //----------------------------------------------//
    public void runStrategyAsLongAsPossible(Nonogram no, int strategyNr) {
        boolean changedSomething;
        do {
            boolean horSuccess = strategyInOneDirection(no, strategyNr, true);
            boolean verSuccess = strategyInOneDirection(no, strategyNr, false);

            System.out.println("Method runStrategyAsLongAsPossible (strategy" + strategyNr + "): " +
                    " horizontally was " + (horSuccess ? "" : "not ") + "successful and vertically was " +
                    (verSuccess ? "" : "not ") + "successful.");
            changedSomething = horSuccess || verSuccess;

        } while (changedSomething);
    }

    /**
     * Here is the actual chooser of the strategy. <br>
     * Add any additional strategies inside the switch-statement in this method.
     *
     * @param no         Nonogram
     * @param strategyNr nr of the strategy (strategy1 is in Solver, strategy2 is in Solver2, strategy3 in Solver3, ...)
     * @param horizontal true for horizontal, false for vertical
     * @return true if something was changed
     */
    public boolean strategyInOneDirection(Nonogram no, int strategyNr, boolean horizontal) {

        //-----------------//
        // pre-conditions: //
        //-----------------//
        if (strategyNr == 2) {
            // strategy2 can only run, after strategy1 was run at least once and placed some marks:
            // check, if a line contains marks:
            if (!no.getHorizontalLine(1).containsMarks()) {
                System.out.println("\n|-------------------------------------------------------|");
                System.out.println("| Error: please run strategy1 before running strategy2! |");
                System.out.println("|-------------------------------------------------------|");
                return false;
            }
        }

        boolean changed = false;

        List<Line> lines = horizontal ? no.getHorizontalLines() : no.getVerticalLines();

        for (Line line : lines) {
            switch (strategyNr) {
                case 1:
                    boolean changedNow = strategy1(line);
                    if (changedNow) {
                        changed = true;
                    }
                    break;
                case 2:
                    Solver2 solver2 = new Solver2();
                    boolean changedForward2 = solver2.moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(line);
                    // reversed direction:
                    boolean changedReversed2 = solver2.moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(line.reversed());
                    if (changedForward2 || changedReversed2) {
                        changed = true;
                    }
                    break;
                case 3:
                    Solver3 solver3 = new Solver3();
                    boolean changedForward3 = solver3.markABoxWhiteIfNotCoveredByAMarking(line);
                    // reversed direction:
                    boolean changedReversed3 = solver3.markABoxWhiteIfNotCoveredByAMarking(line.reversed());
                    if (changedForward3 || changedReversed3) {
                        changed = true;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Undefined strategyNr: " + strategyNr);
            }
        }
        return changed;
    }


    //------------//
    // Strategy 1 //
    //------------//

    /**
     * Strategy 1: look at the EMPTY line's numbers and try to figure out if you can draw some boxes:
     *
     * @param line Line
     * @return true, if it changed a state of a box, or if it changed a mark
     */
    public boolean strategy1(Line line) {

        boolean changedMarks = markLine(line);

        boolean changedBoxStates = markBoxesWhichHaveSameMarksInOppositeDirection(line);

        return changedBoxStates || changedMarks;
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
     * @return True, if a mark was changed
     */
    public boolean markLine(Line line) {

        boolean changedForwardMark = markLineInOneDirection(line);

        Line reversed = line.reversed();
        boolean changedBackwardMark = markLineInOneDirection(reversed);

        line.setContainsMarks(true);

        return changedForwardMark || changedBackwardMark;
    }

    public boolean markLineInOneDirection(Line line) {
        boolean deletedAMark = false;

        List<Number> numbers = line.getNumbers();

        System.out.println("---- Creating Marks in " + line.getDirection() + " line " + line.getLineNumber() +
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
                        boolean currentlyDeleted = deleteAMarkIfItEqualsTheGivenNumberIndex(line, j, numberIndex);
                        if (currentlyDeleted) {
                            deletedAMark = true;
                        }
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

        // return, whether something was changed:
        // for sure, in the first run, there was something changed:
        boolean firstRun = !line.containsMarks();
        // else, if there was a deleted mark while moving a mark; that would also be a change:
        return firstRun || deletedAMark;
    }


    public boolean checkIfAlreadyPassedTheMark(Line line, int position, int numberIndex) {
        for (int i = 0; i <= position; i++) {
            if (line.getMarkForBox(i) == numberIndex) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteAMarkIfItEqualsTheGivenNumberIndex(Line line, int position, int numberIndex) {

        if (line.getMarkForBox(position) == numberIndex) {
            // delete the mark (by setting it to minus 1):
            line.setMarkForBox(position, -1);
            System.out.println("Deleted a mark: " + line.getDirection() + " line-nr " + line.getLineNumber() +
                    ", position=" + position + ", numberIndex=" + numberIndex +
                    ". isLineReversed=" + line.isLineReversed());
            return true;
        }
        return false;
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


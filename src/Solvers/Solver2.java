package Solvers;

import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import NonogramStructure.State;

public class Solver2 {

    public void runStrategy2AsLongAsPossible(Nonogram no) {

        boolean changedSomething = false;

        while (changedSomething) {
            // todo: include the new strategies!

            // todo: include also boolean return, whether something was changed or not -> see Solver.runStrategy1AsLongAsPossible(..).

            // todo: add a separate method which checks, whether a box has no marks AND is not in between the same
            //  marking-number. If found, mark box State.WHITE
        }
    }


    public void strategy2All(Nonogram no) {

        // todo: create test for this!
        // todo: include also boolean return, whether something was changed or not -> see Solver.runStrategy1AsLongAsPossible(..).

        for (Line hLine : no.getHorizontalLines()) {
            moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(hLine);

            // and in reversed direction:
            moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(hLine.reversed());
        }

        for (Line vLine : no.getVerticalLines()) {
            moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(vLine);

            // and in reversed direction:
            moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(vLine.reversed());
        }
    }


    /**
     * For example usage -> see test SolverTest2_On_Nonogram5
     *
     * @param line Line
     */
    public void moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(Line line) {

        for (int i = line.getNumbersSize() - 1; i >= 0; i--) {
            moveMarkForwardIfNonCoveredBlackBoxIsFoundAhead(line, i);
        }
    }

    private void moveMarkForwardIfNonCoveredBlackBoxIsFoundAhead(Line line, int numberIndex) {
        System.out.println("---- Started moveMarkForwardIfNonCoveredBlackBoxIsFoundAhead() in " + line.getDirection() +
                " line " + line.getLineNumber() + ", isLineReversed=" + line.isLineReversed() +
                ", numberIndex=" + numberIndex + " ----");
        // search and move to the position right after last box of that number
        int afterNumber = -1;
        for (int i = 0; i < line.getBoxesSize(); i++) {
            if (line.getMarkForBox(i) == numberIndex) {
                afterNumber = i + line.getNumber(numberIndex).getN();
                break;
            }
        }
        if (afterNumber == -1) {
            throw new IllegalArgumentException("Error: afterNumber was -1!");
        }

        // check if a black box is ahead, until the next number is found (if there is one):
        boolean nextNumberExists = numberIndex + 1 < line.getNumbersSize();
        int nextNumberBegin = -999;
        if (nextNumberExists) {
            for (int i = afterNumber; i < line.getBoxesSize(); i++) {
                if (line.getMarkForBox(i) == numberIndex + 1) {
                    nextNumberBegin = i;
                    break;
                }
            }
        }

        int start = afterNumber;
        // end one box before the nextNumberBegin, if there is a next number, else set the end to the line length - 1:
        int end = nextNumberBegin != -999 ? nextNumberBegin - 1 : line.getBoxesSize() - 1;
        // loop backwards:
        for (int pos = end; pos >= start; pos--) {
            if (line.getBox(pos).getState() == State.BLACK) {
                moveMarkForward(line, numberIndex, afterNumber - 1, pos);
                break;
            }
        }
    }


    private void moveMarkForward(Line line, int numberIndex, int oldEndPositionOfMark, int newEndPositionOfMark) {
        System.out.println("Found a black box, which was not yet covered by a mark! So moveMarkForward(..) was called: " +
                line.getDirection() + " line nr " + line.getLineNumber() + ", numberIndex=" + numberIndex +
                ", oldEndPositionOfMark=" + oldEndPositionOfMark + ", newEndPositionOfMark=" + newEndPositionOfMark);

        // deleting the old marks
        int number = line.getNumber(numberIndex).getN();
        int oldStart = oldEndPositionOfMark - number + 1; // example: end at 7, Number = 3, -> newStart = 7 - 3 + 1 = 5 -> correct
        for (int i = oldStart; i <= oldEndPositionOfMark; i++) {
            if (line.getMarkForBox(i) == numberIndex) {
                // delete the mark:
                line.setMarkForBox(i, -1);
            } else {
                throw new IllegalArgumentException("This mark was not there were it was expected!");
            }
        }

        // creating the new marks
        int newStart = newEndPositionOfMark - number + 1;
        for (int i = newStart; i <= newEndPositionOfMark; i++) {
            if (line.getMarkForBox(i) == -1) {
                line.setMarkForBox(i, numberIndex);
            } else {
                throw new IllegalArgumentException("The box contained already a mark!");
            }
        }
    }
}

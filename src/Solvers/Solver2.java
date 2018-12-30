package Solvers;

import NonogramStructure.Line;
import NonogramStructure.State;


public class Solver2 {

    //------------------------------------------------------------------------------------//
    // Strategy 2 - calling of this strategy works via Solver.strategyInOneDirection(...) //
    //------------------------------------------------------------------------------------//

    /**
     * For example usage -> see test SolverTest2_On_Nonogram5
     *
     * @param line Line
     */
    public boolean moveMarksForwardToCoverAllBlackBoxesWhichAreAhead(Line line) {
        boolean changedSomething = false;
        for (int i = line.getNumbersSize() - 1; i >= 0; i--) {
            boolean changedNow = moveMarkForwardIfNonCoveredBlackBoxIsFoundAhead(line, i);
            if (changedNow) {
                changedSomething = true;
            }
        }
        return changedSomething;
    }

    private boolean moveMarkForwardIfNonCoveredBlackBoxIsFoundAhead(Line line, int numberIndex) {
        // search and move to the position right after last box of that number
        int afterNumber = -1;
        for (int i = 0; i < line.getBoxesSize(); i++) {
            if (line.getMarkForBox(i) == numberIndex) {
                afterNumber = i + line.getNumber(numberIndex).getN();
                break;
            }
        }
        if (afterNumber == -1) {
            throw new IllegalArgumentException("Error: marking of the number with numberIndex " + numberIndex +
                    " was not found, hence afterNumber was -1!");
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
                // that's it, you can return:
                return true;
            }
        }
        return false;
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

package Solvers;

import NonogramStructure.Line;
import NonogramStructure.State;


public class Solver3 {

    //------------------------------------------------------------------------------------//
    // Strategy 3 - calling of this strategy works via Solver.strategyInOneDirection(...) //
    //------------------------------------------------------------------------------------//
    /**
     * checks, whether a box has no marks AND is not in between the same marking-number. If found, mark box State.WHITE
     *
     * @param line Line
     * @return true, if changed something
     */
    public boolean markABoxWhiteIfNotCoveredByAMarking(Line line) {
        boolean changedSomething = false;

        // Between the lastPossiblePosition of the previous number (if there is none, then from position = 0) and
        // the firstPossiblePosition for the current number (in forward direction), there can't be anything.
        // -> mark those boxes white:
        for (int n = 0; n < line.getNumbersSize(); n++) {
            int oneAfterlastPossiblePositionOfPreviousNumber;
            if (n == 0) {
                oneAfterlastPossiblePositionOfPreviousNumber = 0;
            } else {
                oneAfterlastPossiblePositionOfPreviousNumber = line.getLastPossiblePositionForNumber(n - 1) + 1;
            }

            int firstPossiblePosition = line.getFirstPossiblePositionForNumber(n);

            // -> mark those boxes white:
            boolean changedNow = setWhiteMarkForBoxRange(line, oneAfterlastPossiblePositionOfPreviousNumber,
                    firstPossiblePosition - 1);
            if (changedNow) {
                changedSomething = true;
            }
        }

        // After the lastPossiblePosition of the last number, there can not be anything as well -> mark white!
        int lastPossiblePositionOfTheLastNumber = line.getLastPossiblePositionForNumber(line.getNumbersSize() - 1);
        boolean changedNow = setWhiteMarkForBoxRange(line, lastPossiblePositionOfTheLastNumber + 1,
                line.getBoxesSize() - 1);
        if (changedNow) {
            changedSomething = true;
        }

        // NOTE: you don't have to do all this again for reversed; this already handles the marks in both ways!

        return changedSomething;
    }

    /**
     * Note: If start-position is higher than end-position, nothing will be done by this method, and false will
     * be returned.
     *
     * @param line           Line
     * @param startIncluding start position (including)
     * @param endIncluding   end position (including)
     * @return True, if a box was actually set to State.WHITE, if it was not previously set to State.WHITE
     */
    public boolean setWhiteMarkForBoxRange(Line line, int startIncluding, int endIncluding) {
        boolean changedSomething = false;
        for (int i = startIncluding; i <= endIncluding; i++) {
            if (line.getBox(i).getState() == State.BLACK) {
                throw new IllegalArgumentException("Tried to mark a box white, which was already marked black! Box-nr "
                        + i + " in the line: " + line);
            }
            if (line.getBox(i).getState() != State.WHITE) {
                line.getBox(i).setState(State.WHITE);
                changedSomething = true;
            }
        }
        return changedSomething;
    }
}

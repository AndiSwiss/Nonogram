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

        // 1.) Before the firstPossiblePosition for the first number (in forward direction), there can not be anything
        int position = line.getFirstPossiblePositionForNumber(0);

        // -> mark those boxes white:
        if (position > 0) {
            boolean changedNow = setWhiteMarkForBoxRange(line, 0, position - 1);
            if (changedNow) {
                changedSomething = true;
            }
        }

        /* todo:
         *  2.) For each mark: don't draw white marks the space between the firstPossiblePosition of that mark and the
         *      lastPossibleBox of that mark!
         *  3.) After that mark: if there are again boxes without a mark before the second mark starts (between
         *      lastPossiblePosition of current number and firstPossiblePosition of the next number), there can not be anything
         *      -> mark white!
         *      -> this is very similar to 1.)
         *  4.) Repeat 2.) and 3.)
         *  x.) After the lastPossiblePosition of the last mark, there can not be anything as well -> mark white!
         *  NOTE: you don't have to do that again for reversed; this already handles the marks in both ways!
         *
         *
         */


        // todo: implement return statement (if something has changed)
        return changedSomething;
    }

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

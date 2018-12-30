package Solvers;

import NonogramStructure.Line;


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

        // todo: add a separate method which checks, whether a box has no marks AND is not in between the same
        //  marking-number. If found, mark box State.WHITE

        /* todo:
         *  pseudo-code:
         *  1.) Before the first mark (in forward direction), there can not be anything -> mark those boxes white!
         *  2.) For each mark: don't draw white marks the space between the first possible box of that mark and the last
         *      possible box of that mark! (ATTENTION: the last possible box is in the reversed line with the
         *      REVERSED logic!!)
         *  3.) After that mark: if there are again boxes without a mark before the second mark starts (just check the
         *      forward direction), there can not be anything -> mark white!
         *      -> this is essentially the same as 1.)
         *  4.) Repeat 2.) and 3.)
         *  x.) After the last possible place of the last mark, there can not be anything as well -> mark white!
         *  NOTE: you don't have to do that again for reversed; this already handles the marks in both ways!
         *
         */


        // todo: implement return statement (if something has changed)
        return false;
    }
}

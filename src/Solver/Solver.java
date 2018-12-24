package Solver;

import NonogramStructure.*;
import NonogramStructure.Number;

import java.util.List;

public class Solver {

    public void start(Nonogram no) {

        boolean horSuccess = strategy1AllHorizontal(no);
        boolean verSuccess = strategy1AllVertical(no);



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
        for (int i = 0; i < numbers.size(); i++) {
            Number number = numbers.get(i);
            for (int l = 0; l < number.getN(); l++) {
                if (line.getDirection() == Direction.HORIZONTAL) {
                    line.getBox(position).setMarkL(i);
                } else {
                    line.getBox(position).setMarkT(i);
                }
                position++;
            }
            // and move one space in between numbers:
            position++;
        }

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
}


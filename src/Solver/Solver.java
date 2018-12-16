package Solver;

import NonogramStructure.Line;
import NonogramStructure.Nonogram;
import NonogramStructure.Number;

public class Solver {
    private Nonogram no;


    public Solver(Nonogram no) {
        this.no = no;
    }

    public void start() {

//        strategy1(no.getHorizontalLines().get(0));


    }

    /**
     * Strategy 1: look at the line's number and try to figure out if you can draw some boxes:
     * @param line Line
     * @return true, if it changed a state of a box. False if not.
     */
    private boolean strategy1(Line line) {
        int lineNumbersTotal = 0;
        for (Number number : line.getNumberLine().getNumbers()) {
            lineNumbersTotal += number.getN();
        }

        System.out.println("Solver - method strategy1: For line nr " + line.getLineNumber() + " in "
            + line.getDirection().toString().toLowerCase() + " direction, the calculated 'lineNumbersTotal is: "
            + lineNumbersTotal + ".");


        // todo: write the rest of the logic

        return false;


    }
}


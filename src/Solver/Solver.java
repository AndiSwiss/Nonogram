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

        boolean successfull = strategy1(no.getVerticalLines().get(3), true);


    }

    /**
     * Strategy 1: look at the line's number and try to figure out if you can draw some boxes:
     * @param line Line
     * @return true, if it changed a state of a box. False if not.
     */
    private boolean strategy1(Line line, boolean debug) {

        int numbersTotal = 0;
        int maxNumber = 0;
        for (Number number : line.getNumberLine().getNumbers()) {
            numbersTotal += number.getN();
            if (number.getN() > maxNumber) maxNumber = number.getN();
        }

        System.out.printf("line.getBoxesSize() - numbersTotal: %s\n", line.getBoxesSize() - numbersTotal);
        System.out.printf("maxNumber: %s\n", maxNumber);

        // todo: move maxNumber, numberTotal to Class NumberLine, or to class Line
        // todo: maybe get rid of the class NumberLine, this is just confusing. Handle it all inside the class Line

        // todo: numbersTotal  +  each space between numbers (1 * (amountOfNumbers - 1))

        // todo: start with really small nonograms, which are easy and quickly to solve and to understand



        if (debug) {
            System.out.println("Solver - method strategy1: For line nr " + line.getLineNumber() + " in "
                    + line.getDirection().toString().toLowerCase() + " direction.");
            System.out.print("Numbers in this line are: ");
            line.getNumberLine().getNumbers().forEach(n -> System.out.print(n + " "));
            System.out.println();
            System.out.printf("numbersTotal: %s\n", numbersTotal);
            System.out.printf("line.getBoxesSize(): %s\n", line.getBoxesSize());

        }



        // todo: write the rest of the logic

        return false;


    }
}


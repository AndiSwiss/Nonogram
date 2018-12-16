package Solver;

import NonogramStructure.Nonogram;

import java.util.List;

public class Solver {
    private List<List<Integer>> topNumbers;
    private List<List<Integer>> sideNumbers;
    private int horizontalBoxes;
    private int verticalBoxes;


    public Solver(Nonogram no) {
        this.topNumbers = no.topNumbers;
        this.sideNumbers = no.sideNumbers;
        this.horizontalBoxes = no.horizontalBoxes;
        this.verticalBoxes = no.verticalBoxes;
    }

    public void start() {

    }

    private void solveOneLine(List<Integer> line) {

    }
}


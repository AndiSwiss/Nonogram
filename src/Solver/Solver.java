package Solver;

import Data.DataStorage;

import java.util.List;

public class Solver {
    private List<List<Integer>> topNumbers;
    private List<List<Integer>> sideNumbers;
    private int horizontalBoxes;
    private int verticalBoxes;


    public Solver(DataStorage ds) {
        this.topNumbers = ds.topNumbers;
        this.sideNumbers = ds.sideNumbers;
        this.horizontalBoxes = ds.horizontalBoxes;
        this.verticalBoxes = ds.verticalBoxes;
    }

    public void start() {
    }

    private void solveOneLine(List<Integer> line) {

    }
}


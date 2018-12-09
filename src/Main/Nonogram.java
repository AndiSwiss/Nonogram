package Main;

import Draw.Draw_Main;

import static Helpers.FileReader.readAllFileInputs;

/**
 * @author Andreas Ambühl
 * @version 0.1g
 */
public class Nonogram {
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";

        readAllFileInputs(fileName, true);


        // todo: show the solution in the processing-draw -> src/Examples/nonogram1_solution.txt
        // todo: check the drawn solution with the lines I got from the file input
        // todo: create a nonogram-solver

        Draw_Main.main(args);
    }
}

package Main;

import Draw.Draw_Main;

import static Helpers.FileReader.readAllFileInputs;

/**
 * @author Andreas Ambühl
 * @version 0.1f
 */
public class Nonogram {
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";

        readAllFileInputs(fileName, true);

        Draw_Main.main(args);
    }
}

package Main;

import Draw.Draw_Main;

import java.util.List;

import static Helpers.FileHelpers.*;

/**
 * @author Andreas Ambühl
 * @version 0.1e
 */
public class Nonogram {
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";


        List<String> input;
        input = getStringsFromAFile(fileName);

        // calculate width and height of the window:
        int boxSize = Integer.parseInt(input.get(1));
        int horizontalBoxes = Integer.parseInt(input.get(2));
        int verticalBoxes = Integer.parseInt(input.get(3));
        int maxTopNumbers = Integer.parseInt(input.get(4));
        int maxSideNumbers = Integer.parseInt(input.get(5));



        int width = boxSize * (1 + maxSideNumbers + horizontalBoxes + 1);
        int height = boxSize * (1 + maxTopNumbers + verticalBoxes + 1);


        // giving the values to draw:
        Draw_Main.setWidth(width);
        Draw_Main.setHeight(height);
        Draw_Main.setBoxSize(boxSize);
        Draw_Main.setHorizontalBoxes(horizontalBoxes);
        Draw_Main.setVerticalBoxes(verticalBoxes);
        Draw_Main.setMaxTopNumbers(maxTopNumbers);
        Draw_Main.setMaxSideNumbers(maxSideNumbers);
        Draw_Main.main(args);
    }
}

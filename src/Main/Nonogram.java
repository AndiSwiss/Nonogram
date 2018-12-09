package Main;

import Draw.Draw_Main;

import java.util.List;

import static Helpers.FileHelpers.*;

/**
 * @author Andreas Ambühl
 * @version 0.1c
 */
public class Nonogram {
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";


        List<String> input;
        input = getStringsFromAFile(fileName);



        Draw_Main.setWidth(Integer.parseInt(input.get(1)));
        Draw_Main.setHeight(Integer.parseInt(input.get(2)));
        Draw_Main.setBoxSize(Integer.parseInt(input.get(3)));
        Draw_Main.setHorizontalBoxes(Integer.parseInt(input.get(4)));
        Draw_Main.setVerticalBoxes(Integer.parseInt(input.get(5)));
        Draw_Main.main(args);
    }


}

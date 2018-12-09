package Main;

import Draw.Draw_Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Helpers.StringHelpers.Strings.*;

/**
 * @author Andreas Ambühl
 * @version 0.1a
 */
public class Nonogram {
    public static void main(String[] args) {
        // some initial value (in case the scanner would fail)
        int width = 5;
        int height = 5;
        int boxSize = 5;
        String fileName = "Examples/nonogram1.txt";


        List<String> input = new ArrayList<>();
        File file = new File("src/" + fileName);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                input.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        System.out.println("Input so far:");
//        input.forEach(System.out::println);

        //
        for (String s : input) {
            if (s.toLowerCase().contains("width")) {
                width = getLastIntegerFromString(s);
            }

        }

        Draw_Main.setWidth(width);
        Draw_Main.setHeight(500);
        Draw_Main.setBoxSize(10);
//        Draw_Main.main(args);
    }

}

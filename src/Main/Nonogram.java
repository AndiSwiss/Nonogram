package Main;

import Draw.Draw_Main;

import java.util.ArrayList;
import java.util.List;

import static Helpers.FileHelpers.*;
import static Helpers.StringHelpers.*;

/**
 * @author Andreas Ambühl
 * @version 0.1f
 */
public class Nonogram {
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";


        List<String> input;
        input = getStringsFromAFile(fileName);

        // reading the boxSize from the file:
        int boxSize = 0;
        for (String line : input) {
            if (line.contains("boxSize")) {
                boxSize = getLastIntegerFromString(line);
                break;
            }
        }

        // read the topNumbers and the sideNumbers:
        List<List<Integer>> topNumbers = new ArrayList<>();
        List<List<Integer>> sideNumbers = new ArrayList<>();
        boolean startTopNumbers = false;
        boolean startSideNumbers = false;

        for (String line : input) {
            // topNumbers:
            if (line.contains("topNumbers")) {
                startTopNumbers = true;
                continue;
            }

            // sideNumbers:
            if (line.contains("sideNumbers")) {
                startSideNumbers = true;
                continue;
            }

            if (startTopNumbers || startSideNumbers) {
                if (line.length() > 0 && line.charAt(0) >= '0' && line.charAt(0) <= '9') {
                    List<Integer> oneLine = getIntegersFromString(line);
                    if (startTopNumbers) {
                        topNumbers.add(oneLine);
                    } else {
                        sideNumbers.add(oneLine);
                    }
                } else {
                    // stop
                    startTopNumbers = false;
                    startSideNumbers = false;
                }
            }
        }

        // calculating the amount of horizontal and vertical boxes:
        int horizontalBoxes = topNumbers.size();
        int verticalBoxes = sideNumbers.size();

        // calculating maxTopNumbers and maxSideNumbers
        int maxTopNumbers = 0;
        for (List<Integer> one : topNumbers) {
            if (one.size() > maxTopNumbers) {
                maxTopNumbers = one.size();
            }
        }

        int maxSideNumbers = 0;
        for (List<Integer> one : sideNumbers) {
            if (one.size() > maxSideNumbers) {
                maxSideNumbers = one.size();
            }
        }


        // calculate width and height of the window:
        int width = boxSize * (1 + maxSideNumbers + horizontalBoxes + 1);
        int height = boxSize * (1 + maxTopNumbers + verticalBoxes + 1);

        System.out.println("\ntopNumbers:");
        topNumbers.forEach(System.out::println);
        System.out.println("\nsideNumbers:");
        sideNumbers.forEach(System.out::println);

        System.out.println();
        System.out.printf("boxSize: %s\n", boxSize);
        System.out.printf("horizontalBoxes: %s\n", horizontalBoxes);
        System.out.printf("verticalBoxes: %s\n", verticalBoxes);
        System.out.printf("maxTopNumbers: %s\n", maxTopNumbers);
        System.out.printf("maxSideNumbers: %s\n", maxSideNumbers);
        System.out.printf("width: %s\n", width);
        System.out.printf("height: %s\n", height);


        // giving the values to draw:
        Draw_Main.setWidth(width);
        Draw_Main.setHeight(height);
        Draw_Main.setBoxSize(boxSize);
        Draw_Main.setHorizontalBoxes(horizontalBoxes);
        Draw_Main.setVerticalBoxes(verticalBoxes);
        Draw_Main.setMaxTopNumbers(maxTopNumbers);
        Draw_Main.setMaxSideNumbers(maxSideNumbers);
        Draw_Main.setTopNumbers(topNumbers);
        Draw_Main.setSideNumbers(sideNumbers);
        Draw_Main.main(args);
    }
}

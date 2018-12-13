package Data;

import java.util.ArrayList;
import java.util.List;

import static Helpers.FileHelpers.getStringsFromAFile;
import static Helpers.StringHelpers.getIntegersFromString;
import static Helpers.StringHelpers.getLastIntegerFromString;

public class InputData {
    private int myWidth;
    private int myHeight;
    private int boxSize;
    private int horizontalBoxes;
    private int verticalBoxes;
    private int maxTopNumbers;
    private int maxSideNumbers;
    private List<List<Integer>> topNumbers;
    private List<List<Integer>> sideNumbers;

    public void readAllFileInputs(String fileName, boolean debugMode) {
        List<String> input;
        input = getStringsFromAFile(fileName);

        // reading the boxSize from the file:
        boxSize = 0;
        for (String line : input) {
            if (line.contains("boxSize")) {
                boxSize = getLastIntegerFromString(line);
                break;
            }
        }

        // read the topNumbers and the sideNumbers:
        topNumbers = readNumbers(input, "topNumbers");
        sideNumbers = readNumbers(input, "sideNumbers");

        // calculating the amount of horizontal and vertical boxes:
        horizontalBoxes = topNumbers.size();
        verticalBoxes = sideNumbers.size();

        // calculating maxTopNumbers and maxSideNumbers
        maxTopNumbers = 0;
        for (List<Integer> one : topNumbers) {
            if (one.size() > maxTopNumbers) {
                maxTopNumbers = one.size();
            }
        }

        maxSideNumbers = 0;
        for (List<Integer> one : sideNumbers) {
            if (one.size() > maxSideNumbers) {
                maxSideNumbers = one.size();
            }
        }

        // calculate width and height of the window:
        myWidth = boxSize * (1 + maxSideNumbers + horizontalBoxes + 1);
        myHeight = boxSize * (1 + maxTopNumbers + verticalBoxes + 1);

        if (debugMode) {
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
            System.out.printf("width: %s\n", myWidth);
            System.out.printf("height: %s\n", myHeight);
        }
    }

    private static List<List<Integer>> readNumbers(List<String> input, String what) {

        List<List<Integer>> numbers = new ArrayList<>();

        boolean start = false;

        for (String line : input) {
            // search for the start line containing the keyword (such as topNumbers or sideNumbers):
            if (line.contains(what)) {
                start = true;
                continue;
            }

            if (start) {
                if (line.length() > 0 && line.charAt(0) >= '0' && line.charAt(0) <= '9') {
                    List<Integer> oneLine = getIntegersFromString(line);
                    numbers.add(oneLine);
                } else {
                    // stop:
                    break;
                }
            }
        }
        return numbers;
    }

    //-------------------//
    // Getter and Setter //
    //-------------------//


    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public int getHorizontalBoxes() {
        return horizontalBoxes;
    }

    public int getVerticalBoxes() {
        return verticalBoxes;
    }

    public int getMaxTopNumbers() {
        return maxTopNumbers;
    }

    public int getMaxSideNumbers() {
        return maxSideNumbers;
    }

    public List<List<Integer>> getTopNumbers() {
        return topNumbers;
    }

    public List<List<Integer>> getSideNumbers() {
        return sideNumbers;
    }
}

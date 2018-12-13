package Data;

import java.util.ArrayList;
import java.util.List;

import static Helpers.FileHelpers.getStringsFromAFile;
import static Helpers.StringHelpers.getIntegersFromString;
import static Helpers.StringHelpers.getLastIntegerFromString;

public class InputData {
    private String title;
    private int myWidth;
    private int myHeight;
    private int boxSize;
    private int horizontalBoxes;
    private int verticalBoxes;
    private int maxTopNumbers;
    private int maxSideNumbers;
    private List<List<Integer>> topNumbers;
    private List<List<Integer>> sideNumbers;

    /**
     * Reads all the input-data from the file and stores them
     *
     * @param fileName  FileName including the relative path (src/...)
     * @param debugMode If true, then it will print the read data to the terminal.
     */
    public void readAllFileInputs(String fileName, boolean debugMode) {
        List<String> input;
        input = getStringsFromAFile(fileName);

        // reading the title form the file, if set:
        for (String line : input) {
            if (line.toLowerCase().contains("title")) {
                line = line.replace("title: ", "");
                line = line.replace("Title: ", "");
                title = line;
                break;
            }
        }

        // reading the boxSize from the file:
        boxSize = 0;
        for (String line : input) {
            if (line.toLowerCase().contains("boxsize")) {
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
        myHeight = boxSize * (3 + maxTopNumbers + verticalBoxes + 1);

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

    /**
     * Helper method for readAllFileInputs(..)
     */
    private List<List<Integer>> readNumbers(List<String> input, String what) {

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




    public void checkIfInputMatchesSolution(String fileName) {
        List<String> solution;
        fileName = fileName.replace(".txt", "_solution.txt");
        System.out.printf("fileName: %s\n", fileName);
        solution = getStringsFromAFile(fileName);

        // find out the symbol which is used:
        char c = ' ';
        for (String line : solution) {
            // strip all spaces away:
            line = line.trim();
            // if there is still some stuff in the line:
            if (line.length() > 0) {
                c = line.charAt(0);
                break;
            }
        }

        System.out.println("\nSymbol used in the Solution: " + c);

        // checking the side numbers
        for (int i = 0; i < solution.size(); i++) {
            String line = solution.get(i);
            List<Integer> numbers = sideNumbers.get(i);
            int counter = 0;
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == ' ' && counter == 0) {
                    // do nothing, just continue to search for something...
//                    continue;
                } else if (line.charAt(j) == ' ') {
                    // number found, check it:
                    if (numbers.size() == 0) {
                        throw new IllegalArgumentException("ERROR in checkIfInputMatchesSolution: " +
                                "Expected to find another number, but there was none!");
                    }

                    if (numbers.get(0) == counter) {
                        System.out.println("Found the number " + counter + "  in line " + i + ".");
                        counter = 0;
                        numbers.remove(0);
                    } else {
                        throw new IllegalArgumentException("ERROR in checkIfInputMatchesSolution: " +
                                "mismatch of the solution (expected " + numbers.get(0) +  ") and " +
                                "the read numbers (read " + counter + ") from the solution");
                    }
                } else {
                    counter++;
                }
            }

            //todo: better make a separate method which constructs the number from the solution (that I can continue to use!)
            // todo: and then write a separate easy method, which compares those numbers!

            if (counter > 0) {
                // number found, check it:
                if (numbers.size() == 0) {
                    throw new IllegalArgumentException("ERROR in checkIfInputMatchesSolution: " +
                            "Expected to find another number, but there was none!");
                }

                if (numbers.get(0) == counter) {
                    System.out.println("Found the number " + counter + "  in line " + i + ".");
                    counter = 0;
                    numbers.remove(0);
                } else {
                    throw new IllegalArgumentException("ERROR in checkIfInputMatchesSolution: " +
                            "mismatch of the solution (expected " + numbers.get(0) +  ") and " +
                            "the read numbers (read " + counter + ") from the solution");
                }
            }


        }

        // todo: checking the top numbers (you have to check columns!)

    }


    //-------------------//
    // Getter and Setter //
    //-------------------//


    public String getTitle() {
        return title;
    }

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

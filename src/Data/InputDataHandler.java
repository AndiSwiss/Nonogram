package Data;

import java.util.ArrayList;
import java.util.List;

import static Data.DataStorage.*;
import static Data.InitialData.*;


import static Helpers.FileHelpers.getStringsFromAFile;
import static Helpers.StringHelpers.getIntegersFromString;
import static Helpers.StringHelpers.getLastIntegerFromString;

/**
 * Reads all data from the given input-File and stores and/or processes them to be stored in /Data/DataStorage
 */
public class InputDataHandler {

    /**
     * Reads all the input-data from the file and stores in Data/DataStorage (in it's static member fields)
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
        myWidth = boxSize * (1 + maxSideNumbers + horizontalBoxes + rightSideWidth + 1);
        myHeight = boxSize * (1 + headerHeight + 1 + maxTopNumbers + verticalBoxes + bottomHeight + footerHeight);

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


    /**
     * Reads the solution file.
     *
     * @param fileName file to read. The appendix ".txt" gets automatically replaced by "_solution.txt"
     */
    public void readSolutionFile(String fileName) {
        fileName = fileName.replace(".txt", "_solution.txt");
        solutionFile = getStringsFromAFile(fileName);
    }


    /**
     * reads the sideNumbers from the solution
     *
     * @return the list with all the sideNumbers
     */
    private List<List<Integer>> readSideNumbersFromSolution() {
        List<List<Integer>> sideFromSol = new ArrayList<>();

        for (String line : solutionFile) {
            sideFromSol.add(new ArrayList<>());
            int current = 0;
            for (char c : line.toCharArray()) {
                //noinspection Duplicates
                if (c != ' ') {
                    current++;
                } else if (current > 0) {
                    sideFromSol.get(sideFromSol.size() - 1).add(current);
                    current = 0;
                }

            }
            if (current > 0) {
                sideFromSol.get(sideFromSol.size() - 1).add(current);
            }
        }
/*
        System.out.println("\nSide Numbers from solution:");
        for (int i = 0; i < sideFromSol.size(); i++) {
            System.out.print("Line " + i + ": ");
            for (int n : sideFromSol.get(i)) {
                System.out.print(n + ", ");
            }
            System.out.println();
        }
*/
        return sideFromSol;
    }

    /**
     * reads the sideNumbers from the solution
     *
     * @return the list with all the sideNumbers
     */
    private List<List<Integer>> readTopNumbersFromSolution() {
        List<List<Integer>> topFromSol = new ArrayList<>();

        // find max width:
        int maxWidth = 0;
        for (String line : solutionFile) {
            if (line.length() > maxWidth) maxWidth = line.length();
        }

        for (int i = 0; i < maxWidth; i++) {
            topFromSol.add(new ArrayList<>());
            int current = 0;
            for (String s : solutionFile) {
                // go through the column:
                try {
                    char c = s.charAt(i);
                    //noinspection Duplicates
                    if (c != ' ') {
                        current++;
                    } else if (current > 0) {
                        topFromSol.get(topFromSol.size() - 1).add(current);
                        current = 0;

                    }
                } catch (Exception e) {
                    // if array out of index - error
                    if (current > 0) {
                        topFromSol.get(topFromSol.size() - 1).add(current);
                        current = 0;
                    }
                }
            }
            if (current > 0) {
                topFromSol.get(topFromSol.size() - 1).add(current);
            }
        }
/*
        System.out.println("\nTop Numbers from solution:");
        for (int i = 0; i < topFromSol.size(); i++) {
            System.out.print("Line " + i + ": ");
            for (int n : topFromSol.get(i)) {
                System.out.print(n + ", ");
            }
            System.out.println();
        }
*/
        return topFromSol;
    }


    /**
     * Checks if sideNumbers and topNumbers are the same as the ones read from the solution file
     *
     * @throws IllegalArgumentException If there are differences found (actually the called sub-methods
     *                                  throw those errors).
     */
    public void checkIfInputMatchesSolution() {


        List<List<Integer>> sideNumbersFromSolution = readSideNumbersFromSolution();
        List<List<Integer>> topNumbersFromSolution = readTopNumbersFromSolution();

        // check if those lists match the already read lines:
        if (compareTwoListLists(sideNumbers, sideNumbersFromSolution)) {
            System.out.println("sideNumbers is the same as sideNumbersFromSolution.");
        }
        if (compareTwoListLists(topNumbers, topNumbersFromSolution)) {
            System.out.println("topNumbers is the same as topNumbersFromSolution.");
        }


    }

    /**
     * Compares two List<List<Integer> lists.
     *
     * @param l1 list 1
     * @param l2 list 2
     * @return true if the same
     * @throws IllegalArgumentException If they are not the same
     */
    private boolean compareTwoListLists(List<List<Integer>> l1, List<List<Integer>> l2) {
        if (l1.size() != l2.size()) {
            throw new IllegalArgumentException("Compared lists have not the same size! l1: "
                + l1.size() + ", l2: " + l2.size());
        }

        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i).size() != l2.get(i).size()) {
                throw new IllegalArgumentException("Compared lists differ in line " + i + ": not the same size!");
            }
        }

        for (int i = 0; i < l1.size(); i++) {
            int lineLength = l1.get(i).size();
            for (int j = 0; j < lineLength; j++) {
                int n1 = l1.get(i).get(j);
                int n2 = l2.get(i).get(j);
                if (n1 != n2) {
                    throw new IllegalArgumentException("Compared integer in line " + i + " at position " + j
                            + " differ: " + n1 + " vs " + n2);
                }
            }
        }

        return true;
    }
}

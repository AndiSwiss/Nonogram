package Data;

import Helpers.FileHelper;
import Helpers.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads all data from the given input-File and stores and/or processes them to be stored in /Data/DataStorage
 */
public class InputDataHandler {

    /**
     * Reads all the input-data from the file and stores in Data/DataStorage (in it's static member fields)
     *
     * @param fileName  FileName including the relative path (src/...)
     * @param ds        DataStorage
     * @param debugMode If true, then it will print the read data to the terminal.
     */
    public void readAllFileInputs(String fileName, DataStorage ds, boolean debugMode) {
        List<String> input;
        FileHelper fh = new FileHelper();
        input = fh.getStringsFromAFile(fileName);

        // reading the title form the file, if set:
        for (String line : input) {
            if (line.toLowerCase().contains("title")) {
                line = line.replace("title: ", "");
                line = line.replace("Title: ", "");
                ds.title = line;
                break;
            }
        }

        // reading the boxSize from the file:
        ds.boxSize = 0;
        for (String line : input) {
            if (line.toLowerCase().contains("boxsize")) {
                StringHelper sh = new StringHelper();
                ds.boxSize = sh.getLastIntegerFromString(line);
                break;
            }
        }

        // read the topNumbers and the sideNumbers:
        ds.topNumbers = readNumbers(input, "topNumbers");
        ds.sideNumbers = readNumbers(input, "sideNumbers");

        // calculating the amount of horizontal and vertical boxes:
        ds.horizontalBoxes = ds.topNumbers.size();
        ds.verticalBoxes = ds.sideNumbers.size();

        // calculating maxTopNumbers and maxSideNumbers
        ds.maxTopNumbers = 0;
        for (List<Integer> one : ds.topNumbers) {
            if (one.size() > ds.maxTopNumbers) {
                ds.maxTopNumbers = one.size();
            }
        }

        ds.maxSideNumbers = 0;
        for (List<Integer> one : ds.sideNumbers) {
            if (one.size() > ds.maxSideNumbers) {
                ds.maxSideNumbers = one.size();
            }
        }

        if (debugMode) {
            System.out.println("\ntopNumbers:");
            ds.topNumbers.forEach(System.out::println);
            System.out.println("\nsideNumbers:");
            ds.sideNumbers.forEach(System.out::println);

            System.out.println();
            System.out.printf("boxSize: %s\n", ds.boxSize);
            System.out.printf("horizontalBoxes: %s\n", ds.horizontalBoxes);
            System.out.printf("verticalBoxes: %s\n", ds.verticalBoxes);
            System.out.printf("maxTopNumbers: %s\n", ds.maxTopNumbers);
            System.out.printf("maxSideNumbers: %s\n", ds.maxSideNumbers);
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
                    StringHelper sh = new StringHelper();
                    List<Integer> oneLine = sh.getIntegersFromString(line);
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
     * @return List<String> of the solution file
     */
    public List<String> readSolutionFile(String fileName) {
        fileName = fileName.replace(".txt", "_solution.txt");
        FileHelper fh = new FileHelper();
        return fh.getStringsFromAFile(fileName);
    }


    /**
     * reads the sideNumbers from the solution
     *
     * @param solutionFile solutionFile
     * @return the list with all the sideNumbers
     */
    private List<List<Integer>> readSideNumbersFromSolution(List<String> solutionFile) {
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
     * @param solutionFile solutionFile
     * @return the list with all the sideNumbers
     */
    private List<List<Integer>> readTopNumbersFromSolution(List<String> solutionFile) {
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
     * @param ds DataStorage
     * @throws IllegalArgumentException If there are differences found (actually the called sub-methods
     *                                  throw those errors).
     */
    public void checkIfInputMatchesSolution(DataStorage ds) {


        List<List<Integer>> sideNumbersFromSolution = readSideNumbersFromSolution(ds.solutionFile);
        List<List<Integer>> topNumbersFromSolution = readTopNumbersFromSolution(ds.solutionFile);

        // check if those lists match the already read lines:
        if (compareTwoListLists(ds.sideNumbers, sideNumbersFromSolution)) {
            System.out.println("sideNumbers is the same as sideNumbersFromSolution.");
        }
        if (compareTwoListLists(ds.topNumbers, topNumbersFromSolution)) {
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

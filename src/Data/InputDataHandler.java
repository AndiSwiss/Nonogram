package Data;

import Helpers.FileHelper;
import Helpers.StringHelper;
import NonogramStructure.*;
import NonogramStructure.Number;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads all data from the given input-File and stores and/or processes them to be stored in Nonogram
 */
public class InputDataHandler {

    /**
     * Reads all the input-data from the file and stores in Nonogram
     *
     * @param fileName FileName including the relative path (/Examples/...)
     * @return Nonogram, freshly constructed or throws a FileNotFoundException-error, if the specified file was not found
     *
     */
    public Nonogram readAllFileInputs(String fileName) {

        FileHelper fh = new FileHelper();
        List<String> input;
        try {
            input = fh.getStringsFromAFile(fileName);

            String title = null;
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
            int boxSize = 0;
            for (String line : input) {
                if (line.toLowerCase().contains("boxsize")) {
                    StringHelper sh = new StringHelper();
                    boxSize = sh.getLastIntegerFromString(line);
                    break;
                }
            }

            // read the topNumbers and the sideNumbers:
            List<NumberLine> topNumbers = readNumbers(input, "topNumbers");
            List<NumberLine> sideNumbers = readNumbers(input, "sideNumbers");


            return new Nonogram(title, topNumbers, sideNumbers, boxSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Helper method for readAllFileInputs(..)
     */
    private List<NumberLine> readNumbers(List<String> input, String what) {

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
        // convert the list of list of integers to a list of NumberLine:
        return convertListListOfIntegerToListOfNumberLine(numbers);
    }

    /**
     * Converter:
     *
     * @param input List<List<Integer>>
     * @return List<NumberLine>
     */
    private List<NumberLine> convertListListOfIntegerToListOfNumberLine(List<List<Integer>> input) {
        List<NumberLine> result = new ArrayList<>();
        for (List<Integer> line : input) {
            List<Number> numbers = new ArrayList<>();
            for (int n : line) {
                numbers.add(new Number(n));
            }
            result.add(new NumberLine(numbers));
        }
        return result;
    }


    /**
     * Reads the solution file.
     *
     * @param fileName file to read. The appendix ".txt" gets automatically replaced by "_solution.txt"
     * @return Nonogram. It returns null, if no solution was found at the specified location!
     */
    public Nonogram readSolutionFile(String fileName, int boxSize) {
        fileName = fileName.replace(".txt", "_solution.txt");
        FileHelper fh = new FileHelper();
        List<String> solutionStrings;
        try {
            solutionStrings = fh.getStringsFromAFile(fileName);
            // read the topNumbers and sideNumbers from the file:
            List<NumberLine> sideNumbersFromSolution = readSideNumbersFromSolution(solutionStrings);
            List<NumberLine> topNumbersFromSolution = readTopNumbersFromSolution(solutionStrings);

            Nonogram solution = new Nonogram(fileName, topNumbersFromSolution, sideNumbersFromSolution, boxSize);

            // read the solution and set the boxes in the Nonogram appropriately:
            List<Line> horizontalLines = solution.getHorizontalLines();
            for (int y = 0; y < horizontalLines.size(); y++) {
                Line line = horizontalLines.get(y);
                String solutionLine = solutionStrings.get(y);
                List<Box> boxes = line.getBoxes();
                for (int x = 0; x < boxes.size(); x++) {
                    Box box = boxes.get(x);
                    if (solutionLine.length() > x && solutionLine.charAt(x) != ' ') {
                        box.setState(State.BLACK);
                    } else {
                        box.setState(State.WHITE);
                    }
                }
            }

            return solution;

        } catch (FileNotFoundException e) {
            return null;
        }
    }


    /**
     * Reads the sideNumbers from the solution
     *
     * @param solutionFile solutionFile
     * @return the list with all the sideNumbers
     */
    private List<NumberLine> readSideNumbersFromSolution(List<String> solutionFile) {
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
        // convert the list of list of integers to a list of NumberLine:
        return convertListListOfIntegerToListOfNumberLine(sideFromSol);
    }

    /**
     * reads the sideNumbers from the solution
     *
     * @param solutionFile solutionFile
     * @return the list with all the sideNumbers
     */
    private List<NumberLine> readTopNumbersFromSolution(List<String> solutionFile) {
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
        // convert the list of list of integers to a list of NumberLine:
        return convertListListOfIntegerToListOfNumberLine(topFromSol);
    }


    /**
     * Checks if sideNumbers and topNumbers are the same as the ones read from the solution file
     *
     * @param no Nonogram
     * @throws IllegalArgumentException If there are differences found (actually the called sub-methods
     *                                  throw those errors).
     */
    public void checkIfInputMatchesSolution(Nonogram no, Nonogram solutionFile) {


        List<NumberLine> sideNumbersFromSolution = solutionFile.getSideNumbers();
        List<NumberLine> topNumbersFromSolution = solutionFile.getTopNumbers();

        // check if those lists match the already read lines:
        if (compareTwoListLists(no.getSideNumbers(), sideNumbersFromSolution)) {
            System.out.println("sideNumbers is the same as sideNumbersFromSolution.");
        }
        if (compareTwoListLists(no.getTopNumbers(), topNumbersFromSolution)) {
            System.out.println("topNumbers is the same as topNumbersFromSolution.");
        }
    }

    /**
     * Compares two List<NumberLine> lists.
     *
     * @param l1 list 1
     * @param l2 list 2
     * @return true if the same
     * @throws IllegalArgumentException If they are not the same
     */
    private boolean compareTwoListLists(List<NumberLine> l1, List<NumberLine> l2) {
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

                Number n1 = l1.get(i).get(j);
                Number n2 = l2.get(i).get(j);
                if (!n1.equals(n2)) {
                    throw new IllegalArgumentException("Compared integer in line " + i + " at position " + j
                            + " differ: " + n1.getN() + " vs " + n2.getN());
                }
            }
        }

        return true;
    }
}

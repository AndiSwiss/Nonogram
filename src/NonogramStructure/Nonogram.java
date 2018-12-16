package NonogramStructure;

import java.util.List;
import java.util.Objects;

/**
 * Data storage for various values, which have to be set, changed or accessed across different classes. <br>
 * An instance of this gets created in Nonogram and saved there as a private variable. <br>
 * If any other class needs access, the Nonogram-class should pass that instance (or just some specific fields)
 * to that class-constructor or its class-method.
 */
public class Nonogram {

    private String title;
    private int horizontalBoxes;
    private int verticalBoxes;

    private int maxTopNumbers;
    private int maxSideNumbers;
    private List<NumberLine> topNumbers;
    private List<NumberLine> sideNumbers;

    // todo: change type to 'nonogram' and save elsewhere (such as a private member in the solver, or DrawMain or?)
    private List<String> solutionFile;

    public int boxSize;


    /**
     * Constructor
     *
     * @param title       title
     * @param topNumbers  topNumbers
     * @param sideNumbers sideNumbers
     * @param boxSize     boxSize
     */
    public Nonogram(String title, List<NumberLine> topNumbers, List<NumberLine> sideNumbers, int boxSize) {
        this.title = title;
        this.topNumbers = topNumbers;
        this.sideNumbers = sideNumbers;
        this.boxSize = boxSize;

        // calculating the amount of horizontal and vertical boxes:
        horizontalBoxes = topNumbers.size();
        verticalBoxes = sideNumbers.size();

        // calculating maxTopNumbers and maxSideNumbers
        maxTopNumbers = 0;
        for (NumberLine one : topNumbers) {
            if (one.size() > maxTopNumbers) {
                maxTopNumbers = one.size();
            }
        }

        maxSideNumbers = 0;
        for (NumberLine one : sideNumbers) {
            if (one.size() > maxSideNumbers) {
                maxSideNumbers = one.size();
            }
        }
    }

    //----------------//
    // Custom Methods //
    //----------------//
    /**
     * Changes the boxSize.
     * @param value A positive value increases the boxSize, a negative value decreases the boxSize.
     */
    public void changeBoxSize(int value) {
        boxSize += value;
    }

    //---------//
    // Setters //
    //---------//
    public void setSolutionFile(List<String> solutionFile) {
        this.solutionFile = solutionFile;
    }


    //---------//
    // Getters //
    //---------//
    public String getTitle() {
        return title;
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

    public List<NumberLine> getTopNumbers() {
        return topNumbers;
    }

    public List<NumberLine> getSideNumbers() {
        return sideNumbers;
    }

    public List<String> getSolutionFile() {
        return solutionFile;
    }

    public int getBoxSize() {
        return boxSize;
    }



    // todo: rewrite the equals method, so that it really makes sense!

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nonogram nonogram = (Nonogram) o;
        return horizontalBoxes == nonogram.horizontalBoxes &&
                verticalBoxes == nonogram.verticalBoxes &&
                maxTopNumbers == nonogram.maxTopNumbers &&
                maxSideNumbers == nonogram.maxSideNumbers &&
                Objects.equals(topNumbers, nonogram.topNumbers) &&
                Objects.equals(sideNumbers, nonogram.sideNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horizontalBoxes, verticalBoxes, maxTopNumbers, maxSideNumbers, topNumbers, sideNumbers);
    }
}

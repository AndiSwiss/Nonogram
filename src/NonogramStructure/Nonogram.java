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

    public String title;
    public int horizontalBoxes;
    public int verticalBoxes;

    // todo: remove maxTopNumbers and maxSideNumbers and replace with getters, which get the size dynamically from topNumbers / sideNumbers
    public int maxTopNumbers;
    public int maxSideNumbers;
    public List<NumberLine> topNumbers;
    public List<NumberLine> sideNumbers;

    // todo: change type to 'nonogram'
    public List<String> solutionFile;

    public int boxSize;


    // todo: create a constructor, which takes the arguments title, topNumbers and sideNumbers and boxSize
    // todo: make everything private!
    // todo: from that you can construct the amount horizontalBoxes, the verticalBoxes
    // todo: the solutionFile should also have private access, with setter and getter


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

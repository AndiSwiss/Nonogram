package NonogramStructure;

import java.util.List;

/**
 * Data storage for various values, which have to be set, changed or accessed across different classes. <br>
 * An instance of this gets created in Nonogram and saved there as a private variable. <br>
 * If any other class needs access, the Nonogram-class should pass that instance (or just some specific fields)
 * to that class-constructor or its class-method.
 */
public class Nonogram {

    public String title;

    // todo: remove boxSize from this class -> this is in the wrong place!
    public int boxSize;

    public int horizontalBoxes;
    public int verticalBoxes;
    public int maxTopNumbers;
    public int maxSideNumbers;
    public List<List<Integer>> topNumbers;
    public List<List<Integer>> sideNumbers;
    public List<String> solutionFile;



}
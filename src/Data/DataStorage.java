package Data;

import java.util.List;

/**
 * Data storage for various values, which have to be set, changed or accessed across different classes. <br>
 * -> Easily use them anywhere with the following import-statement: <br>
 * import static Data.InitialData.*;
 */
public class DataStorage {

    public String title;
    public int boxSize;
    public int horizontalBoxes;
    public int verticalBoxes;
    public int maxTopNumbers;
    public int maxSideNumbers;
    public List<List<Integer>> topNumbers;
    public List<List<Integer>> sideNumbers;
    public List<String> solutionFile;


    // for tracking the mouse:
    public Position mousePressedPos = null;
}

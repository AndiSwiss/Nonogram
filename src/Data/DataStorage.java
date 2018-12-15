package Data;

import java.util.List;

/**
 * Data storage for various values, which have to be set, changed or accessed across different classes. <br>
 * -> Easily use them anywhere with the following import-statement: <br>
 * import static Data.InitialData.*;
 */
public class DataStorage {

    public static String title;
    public static int boxSize;
    public static int horizontalBoxes;
    public static int verticalBoxes;
    public static int maxTopNumbers;
    public static int maxSideNumbers;
    public static List<List<Integer>> topNumbers;
    public static List<List<Integer>> sideNumbers;
    public static List<String> solutionFile;

}

package Data;

import Draw.UiElement;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {

    public static String title;
    public static int myWidth;
    public static int myHeight;
    public static int boxSize;
    public static int horizontalBoxes;
    public static int verticalBoxes;
    public static int maxTopNumbers;
    public static int maxSideNumbers;
    public static List<List<Integer>> topNumbers;
    public static List<List<Integer>> sideNumbers;
    public static List<String> solutionFile;

    // some color values:
    public static final int cBlack = 0;
    public static final int cDarkGrey2 = 40;
    public static final int cDarkGrey = 80;
    public static final int cGrey = 127;
    public static final int cLightGrey = 180;
    public static final int cWhite = 255;


    // for interactive UI:
    public static int headerHeight = 3;
    public static int rightSideWidth = 3;
    public static int bottomHeight = 5;
    public static int footerHeight = 3;
    public static List<UiElement> uiElements = new ArrayList<>();
    public static int mousePressedX = -1;
    public static int mousePressedY = -1;

}

package Data;


/**
 * Write all the initial data here. <br>
 * -> Easily use them anywhere with the following import-statement: <br>
 * import static Data.InitialData.*;
 */
public class InitialData {
    // some color values:
    public static final int cBlack = 0;
    public static final int cDarkGrey2 = 40;
    public static final int cDarkGrey = 80;
    public static final int cGrey = 127;
    public static final int cLightGrey = 170;
    public static final int cLightGrey2 = 210;
    public static final int cLightGrey3 = 235;
    public static final int cWhite = 255;

    // some specifically used colors. Can be changed here for all corresponding elements:
    public static final int cBackground = cLightGrey3;
    public static final int cBackgroundLine = cDarkGrey;
    public static final int cZoneOutline = cDarkGrey;


    // for interactive UI:
    public static int headerHeight = 3;
    public static int rightSideWidth = 5;
    public static int bottomHeight = 7;
    public static int footerHeight = 3;
    public static int mousePressedX = -1;
    public static int mousePressedY = -1;

    public static String footerText = "© 2018 by Andreas Ambühl";
}

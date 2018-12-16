package Data;


/**
 * Write all the initial data here. <br>
 * -> Easily use them anywhere with the following import-statement: <br>
 * import static Data.InitialData.*;
 */
@SuppressWarnings("WeakerAccess")
public class InitialData {
    // window size:
    public static final int myWidth = 1200;
    public static final int myHeight = 1000;

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
    public static final int cUiNotSelected = cLightGrey2;
    public static final int cUiSelected = cLightGrey;


    // for interactive UI:
    public static final int headerHeight = 3;
    public static final int rightSideWidth = 5;
    public static final int footerHeight = 1;
    public static final String footerText = "© 2018 by Andreas Ambühl";

}

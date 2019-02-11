package Data;


import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Write all the initial data here. <br>
 * An instance of this gets created in Nonogram and saved there as a private variable. <br>
 * If any other class needs access, the Nonogram-class should pass that instance (or just some specific fields)
 * to that class-constructor or its class-method.
 */
@SuppressWarnings("WeakerAccess")
public class InitialData {
    // window size:
    public final int myWidth = 1200;
    public final int myHeight = 1000;

    // some color values:
    public final int cBlack = 0;
    public final int cDarkGrey2 = 40;
    public final int cDarkGrey = 80;
    public final int cGrey = 127;
    public final int cLightGrey = 170;
    public final int cLightGrey2 = 210;
    public final int cLightGrey3 = 235;
    public final int cWhite = 255;

    // some specifically used colors. Can be changed here for all corresponding elements:
    public final int cBackground = cLightGrey3;
    public final int cBackgroundLine = cDarkGrey;
    public final int cZoneOutline = cDarkGrey;
    public final int cUiNotSelected = cLightGrey2;
    public final int cUiSelected = cLightGrey;


    // for interactive UI:
    public final int headerHeight = 3;
    public final int rightSideWidth = 5;
    public final int footerHeight = 1;
    public final String footerText = "© 2018 by Andreas Ambühl";


    // path to the nonogram library:
    private final String current = System.getProperty("user.dir");
    public final Path pathToNonogramLibrary = Paths
            .get(current)
            .getParent()
            .resolve("NonogramLibrary")
            .resolve("converted-files");

    // various:
    public final String initialFileToOpen = "nonogram5";


}

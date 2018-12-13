package Draw;

import Data.InputData;
import processing.core.PApplet;

import java.util.List;

/**
 * @author Andreas Ambühl
 * @version 0.2d
 */
public class Nonogram extends PApplet {

    private static String title;
    private static int myWidth;
    private static int myHeight;
    private static int boxSize;
    private static int horizontalBoxes;
    private static int verticalBoxes;
    private static int maxTopNumbers;
    private static int maxSideNumbers;
    private static List<List<Integer>> topNumbers;
    private static List<List<Integer>> sideNumbers;
    private static List<String> solutionFile;

    // some color values:
    private static int cBlack = 0;
    private static int cThinLine = 127;
    private static int cWhite = 255;


    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";

        InputData data = new InputData();
        data.readAllFileInputs(fileName, true);
        data.readSolutionFile(fileName);

        data.checkIfInputMatchesSolution();

        // todo: get rid of the static fields in this method if possible, so the following won't be necessary
        title = data.getTitle();
        myWidth = data.getMyWidth();
        myHeight = data.getMyHeight();
        boxSize = data.getBoxSize();
        horizontalBoxes = data.getHorizontalBoxes();
        verticalBoxes = data.getVerticalBoxes();
        maxTopNumbers = data.getMaxTopNumbers();
        maxSideNumbers = data.getMaxSideNumbers();
        topNumbers = data.getTopNumbers();
        sideNumbers = data.getSideNumbers();
        solutionFile = data.getSolutionFile();


        // todo: show the solution in the processing-draw -> src/Examples/nonogram1_solution.txt
        // todo: check the drawn solution with the lines I got from the file input
        // todo: create a nonogram-solver

        PApplet.main("Draw.Nonogram");
    }

    @Override
    public void setup() {
        size(myWidth, myHeight);
        frameRate(30);

        drawBackground();
        drawDigits();
        drawTitle();

        drawSolution();

    }



    @Override
    public void draw() {

    }

    @Override
    public void keyPressed() {
        if (keyCode == UP) {
            //
        } else if (keyCode == DOWN) {
            //
        } else if (keyCode == RIGHT) {
            //
        } else if (keyCode == LEFT) {
            //
        }
    }


    //---------------------//
    // Custom Draw Methods //
    //---------------------//

    private void drawTitle() {
        fill(255);
        stroke(255);
        textSize(boxSize);
        text(title, boxSize, (int) (1.5 * boxSize));
    }

    private void drawBackground() {
        fill(255);
        stroke(cThinLine);

        strokeWeight(3);
        // top box:
        rect(boxSize * (1 + maxSideNumbers), 3 * boxSize,
                boxSize * horizontalBoxes, boxSize * maxTopNumbers);

        // side box:
        rect(boxSize, boxSize * (3 + maxTopNumbers),
                boxSize * maxSideNumbers, boxSize * verticalBoxes);


        //main box:
        rect(boxSize * (1 + maxSideNumbers),
                boxSize * (3 + maxTopNumbers),
                boxSize * horizontalBoxes, boxSize * verticalBoxes);

        // thin lines:
        // horizontally:
        strokeWeight(1);
        int initialY = boxSize * (3 + maxTopNumbers);
        for (int i = 1; i < verticalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(2);
            }
            int y = initialY + i * boxSize;
            line(boxSize, y, myWidth - boxSize, y);
            strokeWeight(1);
        }

        // vertically:
        int initialX = boxSize * (1 + maxSideNumbers);
        for (int i = 1; i < horizontalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(2);
            }
            int x = initialX + i * boxSize;
            line(x, 3 * boxSize, x, myHeight - boxSize);
            strokeWeight(1);
        }
    }

    private void drawDigits() {
        // todo: draw '...topNumbers;' and '...sideNumbers':

    }


    private void drawSolution() {
        for (int i = 0; i < solutionFile.size(); i++) {
            String line = solutionFile.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) != ' ') {
                    drawBox(j, i);
                }
            }
        }
    }


    /**
     * Draws a box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param x         x
     * @param y         y
     * @param colorCode color: 0 = white, 1 = black, 2.... other colors
     */
    private void drawBox(int x, int y, int colorCode) {
        stroke(cThinLine);

        // set the color-tone:
        int color;
        switch (colorCode) {
            case 0:
                color = cWhite;
                break;
            case 1:
                color = cBlack;
                break;
            default:
                throw new IllegalArgumentException("Color not defined in method drawBox!");
        }
        fill(color);

        rect(boxSize * (1 + maxSideNumbers + x),
                boxSize * (3 + maxTopNumbers + y) ,
                boxSize,
                boxSize);
    }

    /**
     * Draws a BLACK box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param x x
     * @param y y
     */
    private void drawBox(int x, int y) {
        drawBox(x, y, 1);
    }


}

package Draw;

import Data.InputData;
import processing.core.PApplet;

import java.util.List;

/**
 * @author Andreas Ambühl
 * @version 0.2a
 */
public class Nonogram extends PApplet {

    private static int myWidth;
    private static int myHeight;
    private static int boxSize;
    private static int horizontalBoxes;
    private static int verticalBoxes;
    private static int maxTopNumbers;
    private static int maxSideNumbers;
    private static List<List<Integer>> topNumbers;
    private static List<List<Integer>> sideNumbers;

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram2.txt";

        InputData data = new InputData();
        data.readAllFileInputs(fileName, true);

        // todo:   data.checkIfInputMatchesSolution();

        // todo: get rid of the static fields in this method if possible, so the following won't be necessary
        myWidth = data.getMyWidth();
        myHeight = data.getMyHeight();
        boxSize = data.getBoxSize();
        horizontalBoxes = data.getHorizontalBoxes();
        verticalBoxes = data.getVerticalBoxes();
        maxTopNumbers = data.getMaxTopNumbers();
        maxSideNumbers = data.getMaxSideNumbers();
        topNumbers = data.getTopNumbers();
        sideNumbers = data.getSideNumbers();


        // todo: show the solution in the processing-draw -> src/Examples/nonogram1_solution.txt
        // todo: check the drawn solution with the lines I got from the file input
        // todo: create a nonogram-solver

        PApplet.main("Draw.Draw_Main");
    }

    @Override
    public void setup() {
        size(myWidth, myHeight);
        frameRate(30);

        drawBackground();
        drawDigits();

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
    private void drawBackground() {
        fill(255);
        stroke(127);

        strokeWeight(3);
        // top box:
        rect(boxSize * (1 + maxSideNumbers), boxSize,
                boxSize * horizontalBoxes, boxSize * maxTopNumbers);

        // side box:
        rect(boxSize, boxSize * (1 + maxTopNumbers),
                boxSize * maxSideNumbers, boxSize * verticalBoxes);


        //main box:
        rect(boxSize * (1 + maxSideNumbers),
                boxSize * (1 + maxTopNumbers),
                boxSize * horizontalBoxes, boxSize * verticalBoxes);

        // thin lines:
        // horizontally:
        strokeWeight(1);
        int initialY = boxSize * (1 + maxTopNumbers);
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
            line(x, boxSize, x, myHeight - boxSize);
            strokeWeight(1);
        }
    }

    private void drawDigits() {
        // todo: draw '...topNumbers;' and '...sideNumbers':

    }


}

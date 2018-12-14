package Draw;


import Data.InputData;
import processing.core.PApplet;

import java.util.List;

// all Data is stored in the static object Data (in Package Data):
import static Data.DataStorage.*;


/**
 * @author Andreas Ambühl
 * @version 0.3a
 */
public class Nonogram extends PApplet {

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



        // todo: show the solution in the processing-draw -> src/Examples/nonogram1_solution.txt
        // todo: check the drawn solution with the lines I got from the file input
        // todo: create a nonogram-solver

        PApplet.main("Draw.Nonogram");
    }

    @Override
    public void setup() {
        size(myWidth, myHeight);
        frameRate(30);

        drawAllZoneBoxesForTesting();
//        drawBackground();
//        drawDigits();
//        drawTitle();
//
//        drawSolution();
//
//        buildUiElementList();

    }


    @Override
    public void draw() {

    }

    //-----------------//
    // UI-Interactions //
    //-----------------//
    private void buildUiElementList() {
        // todo: build the UiElements:
        // todo: remove the following test-element:
        int minX = boxSize;
        int minY = boxSize * 3;
        int sizeX = boxSize * 3;
        int sizeY = boxSize * 3;

        fill(cBlack);
        rect(minX, minY, sizeX, sizeY);
        uiElements.add(new UiElement("Test1", minX, minY, sizeX, sizeY));
    }

    @Override
    public void mousePressed() {
        // since this is called multiple times on every click and during the mouse is pressed, store just the first one:
        if (mousePressedX == -1) {
            mousePressedX = mouseX;
            mousePressedY = mouseY;
        }
    }

    @Override
    public void mouseReleased() {
        // check if mousePressedX and ..Y are still in the same UI-Element as mouseReleased:

        // check, if mousePressed was on an UI-Element, and which one:
        UiElement selectedPressed = inWhichUiElementIsIt(mousePressedX, mousePressedY, uiElements);

        if (selectedPressed != null) {
            UiElement selectedReleased = inWhichUiElementIsIt(mouseX, mouseY, uiElements);

            if (selectedPressed.equals(selectedReleased)) {
                // todo: do the action!

                System.out.println("UiElement is successfully clicked: " + selectedPressed);


            }
        }

        // reset mousePressed-values:
        mousePressedX = -1;
        mousePressedY = -1;
    }

    private UiElement inWhichUiElementIsIt(int x, int y, List<UiElement> uiElements) {

        for (UiElement ui : uiElements) {
            if (x >= ui.getMinX()
                    && x <= ui.getMaxX()
                    && y >= ui.getMinY()
                    && y <= ui.getMaxY()) {
                return ui;
            }
        }
        return null;
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

    private void drawAllZoneBoxesForTesting() {
        stroke(cDarkGrey2);
        strokeWeight(3);
        for (Zone z : Zone.values()) {
            fill(z.getBackgroundColor());
            rect(z.getMinX(), z.getMinY(), z.getSizeX(), z.getSizeY());
            fill(cDarkGrey2);
            text(z.getName(), z.getMinX() + boxSize, z.getMinY() + boxSize);
        }
    }

    private void drawOneBox(int x, int y, int color) {
        // todo: define enum Zone:  , MAIN, LEFT, RIGHT, TOP, BOTTOM, FOOTER;
        // todo: a Zone should have defined: Zone-name, minX, minY, sizeX, sizeY, background-color
        // todo: based on this, rewrite and simplify the drawMethods

        // use the MAIN-Zone-Enum for this drawing!:
        Zone main = Zone.MAIN;


    }

    private void drawTitle() {
        fill(cDarkGrey2);
        textAlign(LEFT);
        textSize(boxSize);
        text(title, boxSize, (int) (1.5 * boxSize));
    }

    private void drawBackground() {
        fill(255);
        stroke(cDarkGrey);

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
        stroke(cGrey);
        strokeWeight(1);
        int initialY = boxSize * (3 + maxTopNumbers);
        for (int i = 1; i < verticalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(3);
            }
            int y = initialY + i * boxSize;
            line(boxSize, y, myWidth - boxSize, y);

            // reset the stroke:
            strokeWeight(1);
        }

        // vertically:
        int initialX = boxSize * (1 + maxSideNumbers);
        for (int i = 1; i < horizontalBoxes; i++) {
            if (i % 5 == 0) {
                strokeWeight(3);
            }
            int x = initialX + i * boxSize;
            line(x, 3 * boxSize, x, myHeight - boxSize);
            strokeWeight(1);
        }
    }

    private void drawDigits() {
        // todo: draw '...topNumbers;' and '...sideNumbers':
        textSize((int) (boxSize * 0.7));
        textAlign(CENTER, CENTER);
        fill(cDarkGrey2);

        // sideNumbers:
        for (int i = 0; i < sideNumbers.size(); i++) {
            int y = (int) (boxSize * (3.5 + maxTopNumbers + i));
            List<Integer> line = sideNumbers.get(i);

            for (int j = 0; j < line.size(); j++) {
                int x = (int) (boxSize * (1.5 + j + (maxSideNumbers - line.size())));

                text(line.get(j), x, y);
            }
        }

        // topNumbers:
        for (int i = 0; i < topNumbers.size(); i++) {
            int x = (int) (boxSize * (1.5 + maxSideNumbers + i));
            List<Integer> line = topNumbers.get(i);

            for (int j = 0; j < line.size(); j++) {
                int y = (int) (boxSize * (3.5 + j + (maxTopNumbers - line.size())));

                text(line.get(j), x, y);
            }
        }

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
        stroke(cGrey);

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
                boxSize * (3 + maxTopNumbers + y),
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

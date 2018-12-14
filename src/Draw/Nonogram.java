package Draw;


import Data.InputDataHandler;
import processing.core.PApplet;

import java.util.List;

// all Data is stored in the static object Data (in Package Data):
import static Data.DataStorage.*;
import static Data.InitialData.*;


/**
 * @author Andreas Ambühl
 * @version 0.3c
 */
public class Nonogram extends PApplet {

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        String fileName = "src/Examples/nonogram1.txt";

        InputDataHandler data = new InputDataHandler();
        data.readAllFileInputs(fileName, true);
        data.readSolutionFile(fileName);

        data.checkIfInputMatchesSolution();


        // todo: create a nonogram-solver

        PApplet.main("Draw.Nonogram");
    }

    @Override
    public void setup() {
        size(myWidth, myHeight);
        frameRate(30);
        background(cLightGrey3);

        // For testing, whether the defined zones are ok:
//        Zone.drawAllZoneBoxesForTesting(this);

        drawTitle();

        drawBackground();
        drawFooter();

//        drawDigits();

        drawSolution();

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

    /**
     * Draws a box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param x     x
     * @param y     y
     * @param color color on the black/white color scale
     */
    private void drawBox(int x, int y, int color) {

        drawRectangle(Zone.MAIN, x, y, 1, 1, color, 1, cBackgroundLine);


        // redraw thicker horizontal and vertical lines every five lines:
        // horizontal (on left side of the box):
        if (x % 5 == 0) {
            drawLine(Zone.MAIN, x, y, false, 1, 3, cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (x == horizontalBoxes - 1) {
            drawLine(Zone.MAIN, x + 1, y, false, 1, 3, cBackgroundLine);
        }

        // vertical (on top side of the box):
        if (y % 5 == 0) {
            drawLine(Zone.MAIN, x, y, true, 1, 3, cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (y == verticalBoxes - 1) {
            drawLine(Zone.MAIN, x, y + 1, true, 1, 3, cBackgroundLine);
        }
    }


    /**
     * Draws a BLACK box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param x x
     * @param y y
     */
    private void drawBox(int x, int y) {
        drawBox(x, y, cBlack);
    }

    /**
     * Draws a black rectangle
     */
    private void drawRectangle(Zone zone, int x, int y, int sizeX, int sizeY) {
        drawRectangle(zone, x, y, sizeX, sizeY, cBlack, 1, cBlack);
    }

    private void drawRectangle(Zone zone, int x, int y, int sizeX, int sizeY, int fillColor, int strokeWeight, int strokeColor) {
        fill(fillColor);
        strokeWeight(strokeWeight);
        stroke(strokeColor);
        rect(zone.getMinX() + x * boxSize,
                zone.getMinY() + y * boxSize,
                boxSize * sizeX,
                boxSize * sizeY);
    }

    private void drawLine(Zone zone, int x, int y, boolean horizontal, int length, int strokeWeight, int strokeColor) {
        strokeWeight(strokeWeight);
        stroke(strokeColor);

        int x2;
        int y2;

        if (horizontal) {
            x2 = zone.getMinX() + boxSize * (x + length);
            y2 = zone.getMinY() + boxSize * y;

        } else {
            x2 = zone.getMinX() + boxSize * x;
            y2 = zone.getMinY() + boxSize * (y + length);

        }

        line(zone.getMinX() + boxSize * x,
                zone.getMinY() + boxSize * y,
                x2, y2)
        ;
    }


    private void drawTitle() {
        drawText(title, Zone.HEADER, 0, 0, cBlack);
    }

    private void drawText(String string, Zone zone, int boxX, int boxY, int color, double relativeSize) {
        fill(color);
        textSize((int) (relativeSize * boxSize));
        text(string, zone.getMinX() + boxX * boxSize, zone.getMinY() + (boxY + 1) * boxSize);
    }

    /**
     * Overloaded method
     */
    private void drawText(String string, Zone zone, int boxX, int boxY, int color) {
        drawText(string, zone, boxX, boxY, color, 1);
    }


    private void drawBackground() {

        // top box:
        drawRectangle(Zone.TOP, 0, 0, horizontalBoxes, maxTopNumbers,
                Zone.TOP.getColor(), 3, cZoneOutline);

        // side box:
        drawRectangle(Zone.LEFT, 0, 0, maxSideNumbers, verticalBoxes,
                Zone.LEFT.getColor(), 3, cZoneOutline);

        // main box:
        drawRectangle(Zone.MAIN, 0, 0, horizontalBoxes, verticalBoxes,
                Zone.MAIN.getColor(), 3, cZoneOutline);

        // footer:
        drawRectangle(Zone.FOOTER, 0,0,
                Zone.FOOTER.getSizeX() / boxSize,
                Zone.FOOTER.getSizeY() / boxSize,
                cLightGrey2, 1, cLightGrey2);

        // thin lines:
        // horizontally:
        drawBackgroundLinesInOneZone(Zone.MAIN, true);
        drawBackgroundLinesInOneZone(Zone.MAIN, false);
        drawBackgroundLinesInOneZone(Zone.LEFT, true);
        drawBackgroundLinesInOneZone(Zone.TOP, false);



    }

    private void drawBackgroundLinesInOneZone(Zone zone, boolean horizontal) {
        int weight = 1;

        int length;
        int size;
        if (horizontal) {
            length = zone.getSizeX() / boxSize;
            size = zone.getSizeY() / boxSize;
        } else {
            length = zone.getSizeY() / boxSize;
            size = zone.getSizeX() / boxSize;
        }

        System.out.println("for Zone " + zone.getName() + ", length = " + size);

        for (int i = 1; i < size; i++) {
            if (i % 5 == 0) {
                weight = 3;
            }
            int x;
            int y;
            if (horizontal) {
                x = 0;
                y = i;
            } else {
                x = i;
                y = 0;
            }
            drawLine(zone, x, y, horizontal, length, weight, cBackgroundLine);
            weight = 1;
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
        for (int i = 0; i < verticalBoxes; i++) {
            String line = solutionFile.get(i);
            for (int j = 0; j < horizontalBoxes; j++) {
                // if found an element (and j is still inside the line's length:
                if (j < line.length() && line.charAt(j) != ' ') {
                    drawBox(j, i);
                }
                // else draw a white box:
                else {
                    drawBox(j, i, cWhite);
                }
            }
        }
    }

    private void drawFooter() {
        drawText(footerText, Zone.FOOTER, 1, 1, cDarkGrey);
    }


}

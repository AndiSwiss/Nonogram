package Draw;


import Data.InputDataHandler;
import Data.Position;
import processing.core.PApplet;

import java.util.List;

// all Data is stored in the static object Data (in Package Data):
import static Data.DataStorage.*;
import static Data.InitialData.*;


/**
 * @author Andreas Ambühl
 * @version 0.3f
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

        drawDigits();

        drawSolution();

        System.out.println("Zone Bottom: " + Zone.BOTTOM.debugString());

        // todo: build the UiElementList:
        buildUiElementList();
        drawAllUiElements();


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

        // File chooser:
        drawText("Choose the file:", Zone.BOTTOM, 1,0 - 0.2, 0.8);
        uiElements.add(new UiElement("file: nonogram1", "Example 1",
                new Position(Zone.BOTTOM, 0, 1), 15, 1));
        uiElements.add(new UiElement("file: nonogram2", "Example 2",
                new Position(Zone.BOTTOM, 0, 2), 15, 1));


    }

    private void drawAllUiElements() {
        uiElements.forEach(this::drawUiElement);
    }


    private void drawUiElement(UiElement ui) {
        drawUiElement(ui, false);
    }

    private void drawUiElement(UiElement ui, boolean selected) {

        int color = selected ? cUiSelected : cUiNotSelected;
        
        drawRectangle(ui.getZone(), ui.getRelStartX(), ui.getRelStartY(),
                ui.getRelSizeX(),ui.getRelSizeY(),color, 0, color);
        if (ui.getMessage().length() > 0) {
            drawText(ui.getMessage(), ui.getZone(), ui.getRelStartX() + 1, ui.getRelStartY() - 0.2, 0.8);
        }
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

        // x/y-values from the mouse are absolute positions, whereas the uiElements are in relative positions
        // -> convert everything to absolute positions:


        // todo: the mix of relative and absolute positions doesn't work ->
        // todo: refactor many things to use the new class Position, which includes absolute and relative positions!

        // Zone of the mouse:
        Zone mouseZone = (new Position(x,y)).getZone();

        for (UiElement ui : uiElements) {
//            System.out.println("currently checking on x/y " + x + "/" + y + " ui-element: " + ui);

            if (ui.getZone() == mouseZone) {
                // convert the ui-position in to absolute values:

                if (x >= ui.getAbsStartX()
                        && x <= ui.getAbsEndX()
                        && y >= ui.getAbsStartY()
                        && y <= ui.getAbsEndY()) {
                    return ui;
                }
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
     * Draws a BLACK box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param x x
     * @param y y
     */
    private void drawBox(int x, int y) {
        drawBox(x, y, cBlack);
    }


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
     * Draws a rectangle in a specific zone.
     *
     * @param zone         zone
     * @param x            x relative to zone (is automatically multiplied by boxSize)
     * @param y            y relative to zone (is automatically multiplied by boxSize)
     * @param sizeX        horizontal size
     * @param sizeY        vertical size
     * @param fillColor    color for the fill
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
    private void drawRectangle(Zone zone, int x, int y, int sizeX, int sizeY, int fillColor, int strokeWeight, int strokeColor) {
        fill(fillColor);
        strokeWeight(strokeWeight);
        stroke(strokeColor);
        rect(zone.getMinX() + x * boxSize,
                zone.getMinY() + y * boxSize,
                boxSize * sizeX,
                boxSize * sizeY);
    }

    /**
     * Draw a horizontal or a vertical line inside a specific zone.
     *
     * @param zone         zone
     * @param x            x relative to zone (is automatically multiplied by boxSize)
     * @param y            y relative to zone (is automatically multiplied by boxSize)
     * @param horizontal   true for horizontal, false for vertical
     * @param length       length
     * @param strokeWeight line thickness
     * @param strokeColor  line color
     */
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


    private void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize) {
        fill(cBlack);

        int x = zone.getMinX() + (int) (boxX * boxSize);
        int y = zone.getMinY() + (int) ((boxY + 1) * boxSize);

        textSize((int) (relativeSize * boxSize));
        text(string, x, y);
    }

    /**
     * Overloaded method
     */
    private void drawText(String string, Zone zone, int boxX, int boxY) {
        drawText(string, zone, boxX, boxY, 1);
    }


    /**
     * Draw the background elements
     */
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

        // thin lines:
        // horizontally:
        drawBackgroundLinesInOneZone(Zone.MAIN, true);
        drawBackgroundLinesInOneZone(Zone.MAIN, false);
        drawBackgroundLinesInOneZone(Zone.LEFT, true);
        drawBackgroundLinesInOneZone(Zone.TOP, false);


    }

    /**
     * Draws the background lines in a zone
     *
     * @param zone       zone
     * @param horizontal true is horizontal, false is vertical
     */
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

    /**
     * Draws the digits in sideNumbers and topNumbers
     */
    private void drawDigits() {
        double textSize = 0.7;
        textAlign(CENTER, CENTER);
        fill(cDarkGrey2);

        // sideNumbers:
        for (int i = 0; i < sideNumbers.size(); i++) {
            double y = i - 0.5;
            List<Integer> line = sideNumbers.get(i);

            for (int j = 0; j < line.size(); j++) {
                double x = j + (maxSideNumbers - line.size()) + 0.5;

                drawText(line.get(j).toString(), Zone.LEFT, x, y, textSize);
            }
        }

        // topNumbers:
        for (int i = 0; i < topNumbers.size(); i++) {
            double x = i + 0.5;

            List<Integer> line = topNumbers.get(i);

            for (int j = 0; j < line.size(); j++) {
                double y = j + (maxTopNumbers - line.size()) - 0.5;

                drawText(line.get(j).toString(), Zone.TOP, x, y, textSize);
            }
        }

        // reset alignment:
        textAlign(LEFT);

    }


    /**
     * Draws the solution
     */
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

    /**
     * Draw the title
     */
    private void drawTitle() {
        drawText(title, Zone.HEADER, 0, 0);
    }


    /**
     * Draw the footer-text
     */
    private void drawFooter() {
        // footer-box:
        drawRectangle(Zone.FOOTER, 0, 0,
                Zone.FOOTER.getSizeX() / boxSize,
                Zone.FOOTER.getSizeY() / boxSize,
                cLightGrey2, 1, cLightGrey2);

        // footer-text:
        drawText(footerText, Zone.FOOTER, 1, 1);
    }


}

package Draw;

import Data.InitialData;
import Data.InputDataHandler;
import Data.Position;
import NonogramStructure.Box;
import NonogramStructure.Nonogram;
import NonogramStructure.NumberLine;
import Solver.Solver;
import UiElements.*;

import processing.core.PApplet;

import java.util.List;


/**
 * @author Andreas Ambühl
 * @version 0.6i
 */
public class DrawMain extends PApplet {

    private InitialData id;
    private Nonogram no;
    private Nonogram solutionFile;
    private UiElementList ul;
    private Solver solver;

    // for tracking the mouse:
    private Position mousePressedPos = null;

    //-----------------------------//
    // Processing specific methods //
    //-----------------------------//
    public static void main(String[] args) {
        PApplet.main("Draw.DrawMain");
    }

    @Override
    public void setup() {
        id = new InitialData();
//        no = new Nonogram(); // the nonogram gets initialized in data.readAllFileInputs
        ul = new UiElementList();

        size(id.myWidth, id.myHeight);
        System.out.printf("width: %s, height: %s\n", id.myWidth, id.myHeight);
        frameRate(10);

        ul.buildUiElementList();


        String fileName = "src/Examples/nonogram5.txt";
        // set the first example-Ui to selected:
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().contains("nonogram5"))
                .forEach(ui -> ui.setSelected(true));
        loadNewExample(fileName);
        solver = new Solver();
    }

    public void drawAllZoneBoxes() {
        Zone.drawAllZoneBoxesForTesting(this, id, no);
    }


    public void loadNewExample(String fileName) {

        // reset the drawSolution-Ui to 'not selected':
        ul.getUiElements().stream()
                .filter(ui -> ui.getName().equals("drawSolution"))
                .forEach(ui -> ui.setSelected(false));

        InputDataHandler data = new InputDataHandler();
        no = data.readAllFileInputs(fileName);
        solutionFile = data.readSolutionFile(fileName, no.getBoxSize());

        if (solutionFile != null) {
            data.checkIfInputMatchesSolution(no, solutionFile);
        }

        reDrawUi();

    }

    @Override
    public void draw() {

    }

    //-----------------//
    // UI-Interactions //
    //-----------------//
    private void drawAllUiElements() {
        ul.updateAllUiElementPositions(no.getBoxSize());
        ul.getUiElements().forEach(this::drawUiElement);
    }

    public void drawUiElement(UiElement ui) {

        int color;
        if (ui.isSelected()) {
            color = id.cUiSelected;
        } else {
            color = id.cUiNotSelected;
        }
        if (ui instanceof UiTextbox) {
            color = id.cBackground;
        }

        drawRectangle(ui.getZone(), ui.getRelStartX(), ui.getRelStartY(),
                ui.getRelSizeX(), ui.getRelSizeY(), color, 0, color);
        if (ui.getMessage().length() > 0) {
            drawText(ui.getMessage(), ui.getZone(), ui.getRelStartX() + 1, ui.getRelStartY() - 0.2, 0.8);
        }
    }

    @Override
    public void mousePressed() {
        // since this is called multiple times on every click and during the mouse is pressed, store just the first one:
        if (mousePressedPos == null) {
            mousePressedPos = new Position(mouseX, mouseY, no.getBoxSize());
        }
    }

    @Override
    public void mouseReleased() {
        // check if mousePressedX and ..Y are still in the same UI-Element as mouseReleased:

        // check, if mousePressed was on an UI-Element, and which one:
        UiElement ui = inWhichUiElementIsIt(mousePressedPos, ul.getUiElements());

        if (ui != null) {
            UiElement selectedReleased = inWhichUiElementIsIt(new Position(mouseX, mouseY, no.getBoxSize()), ul.getUiElements());

            if (ui.equals(selectedReleased)) {

                System.out.println("UiElement is successfully clicked: " + ui);

                UiAction uiAction = new UiAction();
                uiAction.action(this, ui, ul, no, solutionFile, solver);
            }
        }

        // reset mousePressed-values:
        mousePressedPos = null;
    }

    public void drawMarks() {
        int markColor = id.cDarkGrey;
        for (int x = 0; x < no.getHorizontalBoxesCount(); x++) {
            for (int y = 0; y < no.getVerticalBoxesCount(); y++) {
                Box box = no.getBox(x, y);
                if (box.getMarkL() != -1) {
                    drawLine(Zone.MAIN, x, y + 0.2, true, 1, 2, markColor);
                }
                if (box.getMarkR() != -1) {
                    drawLine(Zone.MAIN, x, y + 0.8, true, 1, 2, markColor);
                }
                if (box.getMarkT() != -1) {
                    drawLine(Zone.MAIN, x + 0.2, y, false, 1, 2, markColor);
                }
                if (box.getMarkB() != -1) {
                    drawLine(Zone.MAIN, x + 0.8, y, false, 1, 2, markColor);
                }
            }
        }
    }

    public void changeUiSize(int value) {
        no.changeBoxSize(value);

        System.out.println("New boxSize is " + no.getBoxSize());
    }

    /**
     * @param pos        Position in question
     * @param uiElements List of the UI-Elements
     * @return Returns the uiElement, in which the searched position is in, or null if it is in no uiElement
     */
    private UiElement inWhichUiElementIsIt(Position pos, List<UiElement> uiElements) {

        // Zone of the mouse:
        Zone posZone = pos.getZone();

        if (posZone != null) {
            for (UiElement ui : uiElements) {

                // only continue, if the UiElement is in the same zone like mouse:
                if (ui.getZone() == posZone) {

                    // compare the absolute values:
                    if (pos.getAbsX() >= ui.getAbsStartX()
                            && pos.getAbsX() <= ui.getAbsEndX()
                            && pos.getAbsY() >= ui.getAbsStartY()
                            && pos.getAbsY() <= ui.getAbsEndY()) {
                        return ui;
                    }
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

    public void reDrawUi() {
        background(id.cBackground);

        // first update the zones with the new values:
        for (Zone z : Zone.values()) {
            z.updateZone(id, no);
        }

        drawTitle();
        drawBackground();
        drawFooter();
        drawDigits();
        drawAllUiElements();

        // redraw the solution if the corresponding Ui-Element was selected:
        // If that option was not selected, redraw the nonogram:
        boolean drawSolutionSelected = false;
        boolean drawMarksSelected = false;
        for (UiElement ui : ul.getUiElements()) {
            if (ui.getName().contains("drawSolution") && ui.isSelected()) {
                drawSolutionSelected = true;
            } else if (ui.getName().contains("showMarks") && ui.isSelected()) {
                drawMarksSelected = true;
            }
        }

        if (drawSolutionSelected) {
            drawNonogram(solutionFile);
        } else {
            drawNonogram(no);
        }

        if (drawMarksSelected) {
            drawMarks();
        }

    }


    /**
     * Draws a box on the playable field. x/y 0/0 is the top-left corner of the playable field.
     *
     * @param box Box
     */
    private void drawBox(Box box) {
        int x = box.getPosX();
        int y = box.getPosY();

        int color;
        switch (box.getState()) {
            case UNKNOWN:
                color = id.cLightGrey2;
                break;
            case WHITE:
                color = id.cWhite;
                break;
            case BLACK:
                color = id.cBlack;
                break;
            default:
                throw new IllegalArgumentException("Undefined state in method drawBox! Color: " + box.getState());
        }

        drawRectangle(Zone.MAIN, x, y, 1, 1, color, 1, id.cBackgroundLine);


        // redraw thicker horizontal and vertical lines every five lines:
        // horizontal (on left side of the box):
        if (x % 5 == 0) {
            drawLine(Zone.MAIN, x, y, false, 1, 3, id.cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (x == no.getHorizontalBoxesCount() - 1) {
            drawLine(Zone.MAIN, x + 1, y, false, 1, 3, id.cBackgroundLine);
        }

        // vertical (on top side of the box):
        if (y % 5 == 0) {
            drawLine(Zone.MAIN, x, y, true, 1, 3, id.cBackgroundLine);
        }
        // on right side of the box, if it is the last one:
        else if (y == no.getVerticalBoxesCount() - 1) {
            drawLine(Zone.MAIN, x, y + 1, true, 1, 3, id.cBackgroundLine);
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
        rect(zone.getMinX() + x * no.getBoxSize(),
                zone.getMinY() + y * no.getBoxSize(),
                no.getBoxSize() * sizeX,
                no.getBoxSize() * sizeY);
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
    private void drawLine(Zone zone, double x, double y, boolean horizontal, int length, int strokeWeight, int strokeColor) {
        strokeWeight(strokeWeight);
        stroke(strokeColor);

        double x2;
        double y2;

        if (horizontal) {
            x2 = zone.getMinX() + no.getBoxSize() * (x + length);
            y2 = zone.getMinY() + no.getBoxSize() * y;

        } else {
            x2 = zone.getMinX() + no.getBoxSize() * x;
            y2 = zone.getMinY() + no.getBoxSize() * (y + length);

        }

        line(zone.getMinX() + (int) (no.getBoxSize() * x),
                zone.getMinY() + (int) (no.getBoxSize() * y),
                (int) x2, (int) y2)
        ;
    }

    /**
     * Overloaded method
     */
    private void drawText(String string, Zone zone, double boxX, double boxY) {
        drawText(string, zone, boxX, boxY, 1);
    }


    /**
     * Overloaded method
     */
    public void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize) {
        drawText(string, zone, boxX, boxY, relativeSize, id.cBlack);
    }


    private void drawText(String string, Zone zone, double boxX, double boxY, double relativeSize, int color) {
        fill(color);

        int x = zone.getMinX() + (int) (boxX * no.getBoxSize());
        int y = zone.getMinY() + (int) ((boxY + 1) * no.getBoxSize());

        textSize((int) (relativeSize * no.getBoxSize()));
        text(string, x, y);
    }


    /**
     * Draws the main-field with the thin lines
     */
    private void drawEmptyMain() {
        // main box:
        drawRectangle(Zone.MAIN, 0, 0, no.getHorizontalBoxesCount(), no.getVerticalBoxesCount(),
                Zone.MAIN.getColor(), 3, id.cZoneOutline);

        // thin lines:
        drawBackgroundLinesInOneZone(Zone.MAIN, true);
        drawBackgroundLinesInOneZone(Zone.MAIN, false);
    }

    /**
     * Draw the background elements
     */
    private void drawBackground() {

        drawEmptyMain();

        // top box:
        drawRectangle(Zone.TOP, 0, 0, no.getHorizontalBoxesCount(), no.getMaxTopNumbers(),
                Zone.TOP.getColor(), 3, id.cZoneOutline);

        // side box:
        drawRectangle(Zone.LEFT, 0, 0, no.getMaxSideNumbers(), no.getVerticalBoxesCount(),
                Zone.LEFT.getColor(), 3, id.cZoneOutline);

        // thin lines:
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
            length = zone.getSizeX() / no.getBoxSize();
            size = zone.getSizeY() / no.getBoxSize();
        } else {
            length = zone.getSizeY() / no.getBoxSize();
            size = zone.getSizeX() / no.getBoxSize();
        }

//        System.out.println("for Zone " + zone.getName() + ", length = " + size);

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
            drawLine(zone, x, y, horizontal, length, weight, id.cBackgroundLine);
            weight = 1;
        }


    }

    /**
     * Draws the digits in sideNumbers and topNumbers
     */
    private void drawDigits() {
        double textSize = 0.7;
        textAlign(CENTER, CENTER);
        fill(id.cDarkGrey2);

        // sideNumbers:
        for (int i = 0; i < no.getSideNumbers().size(); i++) {
            double y = i - 0.5;
            NumberLine line = no.getSideNumbers().get(i);

            for (int j = 0; j < line.size(); j++) {
                double x = j + (no.getMaxSideNumbers() - line.size()) + 0.5;

                drawText(line.get(j).getNAsString(), Zone.LEFT, x, y, textSize);
            }
        }

        // topNumbers:
        for (int i = 0; i < no.getTopNumbers().size(); i++) {
            double x = i + 0.5;

            NumberLine line = no.getTopNumbers().get(i);

            for (int j = 0; j < line.size(); j++) {
                double y = j + (no.getMaxTopNumbers() - line.size()) - 0.5;

                drawText(line.get(j).getNAsString(), Zone.TOP, x, y, textSize);
            }
        }

        // reset alignment:
        textAlign(LEFT);

    }


    /**
     * Draws a nonogram (the nonogram or the solutionFile)
     */
    public void drawNonogram(Nonogram nonogram) {
        for (int y = 0; y < nonogram.getVerticalBoxesCount(); y++) {
            for (int x = 0; x < nonogram.getHorizontalBoxesCount(); x++) {
                drawBox(nonogram.getBox(x, y));
            }
        }
    }

    /**
     * Draw the title
     */
    private void drawTitle() {
        drawText(no.getTitle(), Zone.HEADER, 0, 0);
    }


    /**
     * Draw the footer-text
     */
    private void drawFooter() {
        // footer-box:
        drawRectangle(Zone.FOOTER, 0, 0,
                Zone.FOOTER.getSizeX() / no.getBoxSize(),
                Zone.FOOTER.getSizeY() / no.getBoxSize(),
                id.cLightGrey2, 1, id.cLightGrey2);

        // footer-text:
        drawText(id.footerText, Zone.FOOTER, 1, -0.2, 0.75, id.cDarkGrey);
    }


}

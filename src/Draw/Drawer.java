package Draw;

import Data.Align;
import Data.InitialData;
import Data.Zone;
import NonogramStructure.Box;
import NonogramStructure.Nonogram;
import NonogramStructure.NumberLine;
import UiElements.UiElement;
import UiElements.UiElementList;
import UiElements.UiTextbox;
import processing.core.PApplet;

public class Drawer {
    private final InitialData id;
    private PApplet p;
    private Nonogram no;
    private Nonogram solutionFile;
    private DrawBasicObjects basicObjects;
    private UiElementList ul;


    //-------------//
    // Constructor //
    //-------------//
    Drawer(PApplet p, Nonogram no, Nonogram solutionFile, UiElementList ul) {
        id = new InitialData();
        this.p = p;
        this.no = no;
        this.solutionFile = solutionFile;
        this.ul = ul;
        basicObjects = new DrawBasicObjects(p, no);
    }


    //--------------//
    // UI-redrawing //
    //--------------//
    public void reDrawUi() {
        p.background(id.cBackground);

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


    //--------------------//
    // UI-Element-drawing //
    //--------------------//
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

        basicObjects.drawRectangle(ui.getZone(), ui.getRelStartX(), ui.getRelStartY(),
                ui.getRelSizeX(), ui.getRelSizeY(), color, 0, color);
        if (ui.getMessage().length() > 0) {
            basicObjects.drawText(ui.getMessage(), ui.getZone(),
                    ui.getRelStartX() + 1, ui.getRelStartY() - 0.2, 0.8);
        }
    }


    //-----------------//
    // Various drawing //
    //-----------------//
    /**
     * Draws a nonogram (the nonogram or the solutionFile)
     */
    private void drawNonogram(Nonogram nonogram) {
        for (int y = 0; y < nonogram.getVerticalBoxesCount(); y++) {
            for (int x = 0; x < nonogram.getHorizontalBoxesCount(); x++) {
                basicObjects.drawBox(nonogram.getBox(x, y));
            }
        }
    }

    public void drawMarks() {
        int markColor = id.cDarkGrey;
        for (int x = 0; x < no.getHorizontalBoxesCount(); x++) {
            for (int y = 0; y < no.getVerticalBoxesCount(); y++) {
                Box box = no.getBox(x, y);
                if (box.getMarkL() != -1) {
                    basicObjects.drawLine(Zone.MAIN, x, y + 0.2, true, 1, 2, markColor);
                }
                if (box.getMarkR() != -1) {
                    basicObjects.drawLine(Zone.MAIN, x, y + 0.8, true, 1, 2, markColor);
                }
                if (box.getMarkT() != -1) {
                    basicObjects.drawLine(Zone.MAIN, x + 0.2, y, false, 1, 2, markColor);
                }
                if (box.getMarkB() != -1) {
                    basicObjects.drawLine(Zone.MAIN, x + 0.8, y, false, 1, 2, markColor);
                }
            }
        }
    }

    /**
     * Draws the digits in sideNumbers and topNumbers
     */
    private void drawDigits() {
        double textSize = 0.7;

        // sideNumbers:
        for (int i = 0; i < no.getSideNumbers().size(); i++) {
            double y = i - 0.5;
            NumberLine line = no.getSideNumbers().get(i);

            for (int j = 0; j < line.size(); j++) {
                double x = j + (no.getMaxSideNumbers() - line.size()) + 0.5;

                basicObjects.drawText(line.get(j).getNAsString(), Zone.LEFT, x, y, textSize, id.cDarkGrey2,
                        Align.CENTER, true);
            }
        }

        // topNumbers:
        for (int i = 0; i < no.getTopNumbers().size(); i++) {
            double x = i + 0.5;

            NumberLine line = no.getTopNumbers().get(i);

            for (int j = 0; j < line.size(); j++) {
                double y = j + (no.getMaxTopNumbers() - line.size()) - 0.5;

                basicObjects.drawText(line.get(j).getNAsString(), Zone.TOP, x, y, textSize, id.cDarkGrey2,
                        Align.CENTER, true);
            }
        }

        // reset alignment:
        p.textAlign(p.LEFT);
    }

    /**
     * Draw the title
     */
    private void drawTitle() {
        basicObjects.drawText(no.getTitle(), Zone.HEADER, 0, 0);
    }

    /**
     * Draw the footer-text
     */
    private void drawFooter() {
        // footer-box:
        basicObjects.drawRectangle(Zone.FOOTER, 0, 0,
                Zone.FOOTER.getSizeX() / no.getBoxSize(),
                Zone.FOOTER.getSizeY() / no.getBoxSize(),
                id.cLightGrey2, 1, id.cLightGrey2);

        // footer-text:
        basicObjects.drawText(id.footerText, Zone.FOOTER, 1, -0.2, 0.75, id.cDarkGrey);
    }


    //--------------------//
    // Background-drawing //
    //--------------------//
    /**
     * Draw the background elements
     */
    private void drawBackground() {

        drawEmptyMain();

        // top box:
        basicObjects.drawRectangle(Zone.TOP, 0, 0, no.getHorizontalBoxesCount(), no.getMaxTopNumbers(),
                Zone.TOP.getColor(), 3, id.cZoneOutline);

        // side box:
        basicObjects.drawRectangle(Zone.LEFT, 0, 0, no.getMaxSideNumbers(), no.getVerticalBoxesCount(),
                Zone.LEFT.getColor(), 3, id.cZoneOutline);

        // thin lines:
        drawBackgroundLinesInOneZone(Zone.LEFT, true);
        drawBackgroundLinesInOneZone(Zone.TOP, false);
    }

    /**
     * Draws the main-field with the thin lines
     */
    private void drawEmptyMain() {
        // main box:
        basicObjects.drawRectangle(Zone.MAIN, 0, 0, no.getHorizontalBoxesCount(), no.getVerticalBoxesCount(),
                Zone.MAIN.getColor(), 3, id.cZoneOutline);

        // thin lines:
        drawBackgroundLinesInOneZone(Zone.MAIN, true);
        drawBackgroundLinesInOneZone(Zone.MAIN, false);
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
            basicObjects.drawLine(zone, x, y, horizontal, length, weight, id.cBackgroundLine);
            weight = 1;
        }
    }

    public void drawTextEntryPopUp(String message) {
        PopUp popUp = new PopUp(basicObjects, no.getBoxSize());
        popUp.textEntryPopUp(message);
    }
}

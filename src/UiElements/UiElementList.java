package UiElements;

import Data.Position;
import Data.Zone;

import java.util.ArrayList;
import java.util.List;

public class UiElementList {

    private List<UiElement> uiElements;


    public void buildUiElementList() {
        uiElements = new ArrayList<>();


        // File chooser:
        uiElements.add(new UiTextbox("fileChooserTitle", "Choose the file:"));
        uiElements.add(new UiFileChooser("Examples/nonogram1.txt", "Example 1"));
        uiElements.add(new UiFileChooser("Examples/nonogram2.txt", "Example 2"));
        uiElements.add(new UiFileChooser("Examples/nonogram3.txt", "Example 3"));
        uiElements.add(new UiFileChooser("Examples/nonogram4.txt", "Example 4"));
        uiElements.add(new UiFileChooser("Examples/nonogram5.txt", "Example 5"));

        // Options:
        uiElements.add(new UiTextbox("emptySpace", ""));
        uiElements.add(new UiTextbox("optionsTitle", "Options:"));
        uiElements.add(new UiSwitchableOption("drawSolution", "Draw the solution"));
        uiElements.add(new UiClickableOption("makeLarger", "Make larger"));
        uiElements.add(new UiClickableOption("makeSmaller", "Make smaller"));
        uiElements.add(new UiSwitchableOption("drawAllZoneBoxes", "Draw all zone boxes"));

        // Solver-UI:
        uiElements.add(new UiTextbox("SolverTitle", "Solver:"));
        uiElements.add(new UiClickableOption("solveNonogram", "Solve the WHOLE nonogram"));
        uiElements.add(new UiClickableOption("solverHorizontalOnce", "Run horizontal solver1 once"));
        uiElements.add(new UiClickableOption("solverVerticalOnce", "Run vertical solver1 once"));
        uiElements.add(new UiClickableOption("solverRunStrategy1AsLongAsPossible", "Run strategy1 as long as possible"));
        uiElements.add(new UiClickableOption("solverRunStrategy2AsLongAsPossible", "Run strategy2 as long as possible"));
        uiElements.add(new UiClickableOption("solverRunStrategy3AsLongAsPossible", "Run strategy3 as long as possible"));
        uiElements.add(new UiPopUpInvoker("solverOneHorizontalLine", "Solve one following horizontal..."));
        uiElements.add(new UiPopUpInvoker("solverOneVerticalLine", "Solve one following vertical line..."));
        uiElements.add(new UiSwitchableOption("showMarks", "Show marks"));
    }


    public void updateAllUiElementPositions(int boxSize) {

        int xOffset = 0;
        int yOffset = 0;

        int relSizeX = 15;

        for (int i = 0; i < uiElements.size(); i++) {
            UiElement ui = uiElements.get(i);

            // position Solver-UI-elements to the side:
            if (ui.getName().equals("SolverTitle")) {
                // cancel out current y-value:
                yOffset = -i;
                // and move the right:
                xOffset = 18;

                // and make a bit larger:
                relSizeX = 20;
            }
            ui.updatePositionValues(new Position(Zone.BOTTOM, xOffset, i + yOffset, boxSize), relSizeX, 1, boxSize);
        }
    }


    /**
     * @param pos Position in question
     * @return Returns the uiElement, in which the searched position is in, or null if it is in no uiElement
     */
    public UiElement inWhichUiElementIsIt(Position pos) {

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

    public List<UiElement> getUiElements() {
        return uiElements;
    }
}


package UiElements;

import Data.Position;
import Draw.Zone;

import java.util.ArrayList;
import java.util.List;

public class UiElementList {

    private List<UiElement> uiElements;


    public void buildUiElementList() {
        uiElements = new ArrayList<>();

        // File chooser:
        uiElements.add(new UiTextbox("fileChooserTitle", "Choose the file:"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram1.txt", "Example 1"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram2.txt", "Example 2"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram3.txt", "Example 3"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram4.txt", "Example 4"));
        uiElements.add(new UiFileChooser("src/Examples/nonogram5.txt", "Example 5"));

        // Solver-UI:
        uiElements.add(new UiTextbox("emptySpace", ""));
        uiElements.add(new UiTextbox("SolverTitle", "Solver:"));
        uiElements.add(new UiClickableOption("solverHorizontalOnce", "Run horizontal solver once"));
        uiElements.add(new UiClickableOption("solverVerticalOnce", "Run vertical solver once"));
        uiElements.add(new UiSwitchableOption("showMarks", "Show marks"));

        // Options:
        uiElements.add(new UiTextbox("optionsTitle", "Options:"));
        uiElements.add(new UiSwitchableOption("drawSolution", "Draw the solution"));
        uiElements.add(new UiClickableOption("makeLarger", "Make larger"));
        uiElements.add(new UiClickableOption("makeSmaller", "Make smaller"));
        uiElements.add(new UiSwitchableOption("drawAllZoneBoxes", "Draw all zone boxes"));

    }


    public void updateAllUiElementPositions(int boxSize) {

        int xOffset = 0;
        int yOffset = 0;

        for (int i = 0; i < uiElements.size(); i++) {
            UiElement ui = uiElements.get(i);

            // position Options-UI-elements to the side:
            if (ui.getName().equals("optionsTitle")) {
                // cancel out current y-value:
                yOffset = -i;
                // and move the right:
                xOffset = 18;
            }

            ui.updatePositionValues(new Position(Zone.BOTTOM, xOffset, i + yOffset, boxSize), 15, 1, boxSize);

        }
    }

    public List<UiElement> getUiElements() {
        return uiElements;
    }
}

